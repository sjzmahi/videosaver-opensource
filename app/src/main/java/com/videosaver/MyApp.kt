package com.videosaver

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.videosaver.di.component.DaggerAppComponent
import com.videosaver.util.AppLogger
import com.videosaver.util.ContextUtils
import com.videosaver.util.FileUtil
import com.videosaver.util.SharedPrefHelper
import com.videosaver.util.downloaders.generic_downloader.DaggerWorkerFactory
import com.yausername.aria2c.Aria2c
import com.yausername.ffmpeg.FFmpeg
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLException
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

open class MyApp : DaggerApplication() {

    companion object {
        const val DEBUG_TAG: String = "YOUTUBE_DL_DEBUG_TAG"
        lateinit var instance: MyApp
    }

    private lateinit var androidInjector: AndroidInjector<out DaggerApplication>

    @Inject
    lateinit var workerFactory: DaggerWorkerFactory

    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    @Inject
    lateinit var fileUtil: FileUtil

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        androidInjector = DaggerAppComponent.builder().application(this).build()
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        androidInjector

    override fun onCreate() {
        super.onCreate()
        instance = this

        ContextUtils.initApplicationContext(applicationContext)

        initializeFileUtils()

        val file: File = fileUtil.folderDir
        val ctx = applicationContext

        WorkManager.initialize(
            ctx,
            Configuration.Builder()
                .setWorkerFactory(workerFactory).build()
        )

        RxJavaPlugins.setErrorHandler { error: Throwable? ->
            AppLogger.e("RxJavaError unhandled $error")
        }

        CoroutineScope(Dispatchers.Default).launch {
            if (!file.exists()) {
                file.mkdirs()
            }

            initializeYoutubeDl()
            updateYoutubeDL()
        }
    }

    private fun initializeFileUtils() {
        val isExternal = sharedPrefHelper.getIsExternalUse()
        val isAppDir = sharedPrefHelper.getIsAppDirUse()

        FileUtil.IS_EXTERNAL_STORAGE_USE = isExternal
        FileUtil.IS_APP_DATA_DIR_USE = isAppDir
        FileUtil.INITIIALIZED = true
    }

    private fun initializeYoutubeDl() {
        try {
            YoutubeDL.getInstance().init(applicationContext)
            FFmpeg.getInstance().init(applicationContext)
            Aria2c.getInstance().init(applicationContext)
        } catch (e: YoutubeDLException) {
            AppLogger.e("failed to initialize youtubedl-android $e")
        }
    }

    private fun updateYoutubeDL() {
//        try {
//            val status = YoutubeDL.getInstance()
//                .updateYoutubeDL(applicationContext, YoutubeDL.UpdateChannel.MASTER)
//            AppLogger.d("UPDATE_STATUS MASTER: $status")
//        } catch (e: Throwable) {
//            e.printStackTrace()
//        }
    }
    public fun isyt(host: String): Boolean {
        if (host == null ) {
            return false
        }
        if (host.toLowerCase().contains("youtu")) {
            return true
        }
        return false
    }
}

