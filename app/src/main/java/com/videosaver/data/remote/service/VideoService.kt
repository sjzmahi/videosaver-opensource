package com.videosaver.data.remote.service

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.common.net.InternetDomainName
import com.videosaver.MyApp
import com.videosaver.R
import com.videosaver.data.local.model.Proxy
import com.videosaver.data.local.model.VideoInfoWrapper
import com.videosaver.data.local.room.entity.VideFormatEntityList
import com.videosaver.data.local.room.entity.VideoFormatEntity
import com.videosaver.data.local.room.entity.VideoInfo
import com.videosaver.util.CookieUtils
import com.videosaver.util.proxy_utils.CustomProxyController
import com.videosaver.util.proxy_utils.OkHttpProxyClient
import com.videosaver.util.AppLogger
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import com.yausername.youtubedl_android.mapper.VideoFormat
import okhttp3.Request
import org.jsoup.Jsoup
import java.util.*
import javax.inject.Inject

interface VideoService {
    fun getVideoInfo(url: Request, isM3u8OrMpd: Boolean = false): VideoInfoWrapper?
}

open class VideoServiceLocal(
    private val proxyController: CustomProxyController, private val helper: YoutubedlHelper
) : VideoService {
    companion object {
        const val MP4_EXT = "mp4"
        private const val FACEBOOK_HOST = ".facebook."
        private const val COOKIE_HEADER = "Cookie"
    }

    override fun getVideoInfo(url: Request, isM3u8OrMpd: Boolean): VideoInfoWrapper? {
        AppLogger.d("Getting info url...:  $url  ${url.headers["Cookie"]}")

        var result: VideoInfoWrapper? = null

        try {
            result = handleYoutubeDlUrl(url, isM3u8OrMpd)
        } catch (e: Throwable) {
            AppLogger.d("YoutubeDL Error: $e")
        }

        return result
    }

    private fun handleYoutubeDlUrl(url: Request, isM3u8OrMpd: Boolean = false): VideoInfoWrapper {
        if (!isM3u8OrMpd && !isYotubeDlSupportedHost(url.url.host)) {
            throw Throwable("host not in supported list")
        }
        if (MyApp.instance.isyt(url.url.host)) {
            Handler(Looper.getMainLooper()).post {
                try {
                    Toast.makeText(MyApp.instance, MyApp.instance.getString(R.string.notsupportyoutube), Toast.LENGTH_LONG).show()
                }catch (e:Throwable ){
                }
            }
            throw Throwable(MyApp.instance.getString(R.string.notsupportyoutube))

        }
        val request = YoutubeDLRequest(url.url.toString())
        url.headers.names().forEach {
            if (it != COOKIE_HEADER) {
                request.addOption("--add-header", "$it:${url.headers[it]}")
            }
        }

        val currentProxy = proxyController.getCurrentRunningProxy()
        if (currentProxy != Proxy.noProxy()) {
            attachProxyToRequest(request, currentProxy)
        }

        val tmpCookieFile = CookieUtils.addCookiesToRequest(url.url.toString(), request)

        try {
            val info = YoutubeDL.getInstance().getInfo(request)
            val formats = info.formats?.map {
                videoEntityFromFormat(
                    it
                )
            }
            val filtered = arrayListOf<VideoFormatEntity>()

            if (url.url.toString().contains(FACEBOOK_HOST)) {
                if (formats != null) {
                    filtered.addAll(formats.filter {
                        it.formatId?.lowercase(Locale.ROOT)?.contains(Regex("hd|sd")) == true
                    })
                }
            }

            val listFormats =
                VideFormatEntityList(filtered.ifEmpty { formats?.filter { !(it.acodec != "none" && it.vcodec == "none") } }
                    ?: emptyList())
            if (listFormats.formats.isEmpty()) throw Exception("Audio Only Detected")

            return VideoInfoWrapper(VideoInfo(title = info.title ?: "no title").also { videoInfo ->
                videoInfo.ext = info.ext ?: MP4_EXT
                videoInfo.thumbnail = info.thumbnail ?: ""
                videoInfo.duration = info.duration.toLong()
                videoInfo.originalUrl = url.url.toString()
                videoInfo.downloadUrls = if (isM3u8OrMpd) emptyList() else listOf(url)
                videoInfo.formats = listFormats
                videoInfo.isRegularDownload = false
            })
        } catch (e: Throwable) {
            throw e
        } finally {
            tmpCookieFile.delete()
        }
    }

    private fun attachProxyToRequest(request: YoutubeDLRequest, currentProxy: Proxy) {
        val user = proxyController.getProxyCredentials().first
        val password = proxyController.getProxyCredentials().second
        if (user.isNotEmpty() && password.isNotEmpty()) {
            request.addOption(
                "--proxy", "https://${user}:${password}@${currentProxy.host}:${currentProxy.port}"
            )
        } else {
            request.addOption(
                "--proxy", "${currentProxy.host}:${currentProxy.port}"
            )
        }
    }

    private fun isYotubeDlSupportedHost(host: String): Boolean {
        return helper.isHostSupported(host)
    }


    private fun videoEntityFromFormat(videoFormat: VideoFormat): VideoFormatEntity {
        return VideoFormatEntity(
            asr = videoFormat.asr,
            tbr = videoFormat.tbr,
            abr = videoFormat.abr,
            format = videoFormat.format,
            formatId = videoFormat.formatId,
            formatNote = videoFormat.formatNote,
            ext = videoFormat.ext,
            preference = videoFormat.preference,
            vcodec = videoFormat.vcodec,
            acodec = videoFormat.acodec,
            width = videoFormat.width,
            height = videoFormat.height,
            fileSize = videoFormat.fileSize,
            fileSizeApproximate = videoFormat.fileSizeApproximate,
            fps = videoFormat.fps,
            url = videoFormat.url,
            manifestUrl = videoFormat.manifestUrl,
            httpHeaders = videoFormat.httpHeaders
        )
    }
}

class YoutubedlHelper @Inject constructor(private val okHttpProxyClient: OkHttpProxyClient) {
    companion object {
        private const val SUPPORTED_SITES_URL =
            "https://ytb-dl.github.io/ytb-dl/supportedsites.html"
    }

    private val sites: HashSet<String> = HashSet()
    private var isLoading = false

    fun isHostSupported(host: String): Boolean {
        if (sites.isEmpty() || isLoading) {
            try {
                loadFromAssets()
            } catch (e: Throwable) {
                e.printStackTrace()
                isLoading = false
            }

            return true
        }

        return try {
            val domainName: InternetDomainName = InternetDomainName.from(host).topPrivateDomain()
            val fixedName = domainName.toString().replace(Regex("\\.\\w{2,}$"), "")

            sites.contains(fixedName) || sites.contains("${fixedName}.com")
        } catch (e: Exception) {
            true
        }
    }

    private fun loadFromAssets() {
        if (!isLoading) {
            isLoading = true

            val response = okHttpProxyClient.getProxyOkHttpClient().newCall(
                Request.Builder().url(SUPPORTED_SITES_URL).build()
            ).execute()
            val doc = Jsoup.parse(response.body.string())
            response.body.close()
            val sitesB = doc.select("li > b")

            for (b in sitesB) {
                val value =
                    b.text().trim().split(":").first().trim().lowercase().replace("- **", "")
                        .replace("**", "").trim()
                sites.add(value)
            }
            isLoading = false
        }
    }
}
