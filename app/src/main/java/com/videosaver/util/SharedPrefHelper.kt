package com.videosaver.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.videosaver.data.local.model.Proxy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject constructor(context: Context) {
    companion object {
        const val PREF_KEY = "settings_prefs"
        private const val IS_DESKTOP = "IS_DESKTOP"
        private const val IS_FIND_BY_URL = "IS_FIND_BY_URL"
        private const val IS_CHECK_EVERY_REQUEST = "IS_CHECK_EVERY_REQUEST"
        private const val IS_AD_BLOCKER = "IS_AD_BLOCKER"
        private const val PROXY_IP_PORT = "PROXY_IP_PORT"
        private const val IS_PROXY_TURN_ON = "IS_PROXY_TURN_ON"
        private const val IS_FIRST_START = "IS_FIRST_START"
        private const val IS_SHOW_VIDEO_ALERT = "IS_SHOW_VIDEO_ALERT"
        private const val IS_SHOW_VIDEO_ACTION_BUTTON = "IS_SHOW_VIDEO_ACTION_BUTTON"
        private const val IS_PRESENT = "IS_PRESENT"
        private const val HOSTS_UPDATE = "HOSTS_UPDATE"
        private const val HOSTS_POPULATED = "HOSTS_POPULATED"
        private const val IS_EXTERNAL_USE = "IS_EXTERNAL_USE"
        private const val IS_APP_DIR_USE = "IS_APP_DIR_USE"
        private const val IS_DARK_MODE = "IS_DARK_MODE"
        const val REGULAR_THREAD_COUNT = "REGULAR_THREAD_COUNT"
        private const val M3U8_THREAD_COUNT = "M3U8_THREAD_COUNT"
        private const val VIDEO_DETECTION_TRESHOLD = "VIDEO_DETECTION_TRESHOLD"
        private const val IS_LOCK_PORTRAIT = "IS_LOCK_PORTRAIT"
    }

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

    fun saveIsDesktop(isDesktop: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_DESKTOP, isDesktop)
            it.apply()
        }
    }

    fun getIsDesktop(): Boolean {
        return sharedPreferences.getBoolean(IS_DESKTOP, false)
    }

    fun saveIsFindByUrl(isFind: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_FIND_BY_URL, isFind)
            it.apply()
        }
    }

    fun isFindVideoByUrl(): Boolean {
        return sharedPreferences.getBoolean(IS_FIND_BY_URL, true)
    }

    fun saveIsCheck(isCheck: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_CHECK_EVERY_REQUEST, isCheck)
            it.apply()
        }
    }

    fun isCheckEveryRequestOnVideo(): Boolean {
        return sharedPreferences.getBoolean(IS_CHECK_EVERY_REQUEST, true)
    }

    fun saveIsAdBlocker(isAdBlocker: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_AD_BLOCKER, isAdBlocker)
            it.apply()
        }
    }

    fun getIsAdBlocker(): Boolean {
        return sharedPreferences.getBoolean(IS_AD_BLOCKER, false)
    }

    fun setCurrentProxy(proxy: Proxy) {
        sharedPreferences.edit().let {
            it.putString(PROXY_IP_PORT, Gson().toJson(proxy.toMap()))
            it.apply()
        }
    }

    fun getCurrentProxy(): Proxy {
        val value = sharedPreferences.getString(PROXY_IP_PORT, "{}") ?: "{}"
        val tmp = Gson().fromJson(value, Map::class.java)
        return Proxy.fromMap(tmp)
    }

    fun getIsProxyOn(): Boolean {
        return sharedPreferences.getBoolean(IS_PROXY_TURN_ON, false)
    }

    fun setIsProxyOn(isTurnedOn: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_PROXY_TURN_ON, isTurnedOn)
            it.apply()
        }
    }

    fun getIsFirstStart(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_START, true)
    }

    fun setIsFirstStart(isFirstStart: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_FIRST_START, isFirstStart)
            it.apply()
        }
    }

    fun isShowVideoAlert(): Boolean {
        return sharedPreferences.getBoolean(IS_SHOW_VIDEO_ALERT, true)
    }

    fun setIsShowVideoAlert(isShow: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_SHOW_VIDEO_ALERT, isShow)
            it.apply()
        }
    }

    fun isShowActionButton(): Boolean {
        return sharedPreferences.getBoolean(IS_SHOW_VIDEO_ACTION_BUTTON, true)
    }

    fun setIsShowActionButton(isShow: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_SHOW_VIDEO_ACTION_BUTTON, isShow)
            it.apply()
        }
    }

    fun getIsPresent(): Boolean {
        return sharedPreferences.getBoolean(IS_PRESENT, true)
    }

    fun setIsPresent(isPresent: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_PRESENT, isPresent)
            it.apply()
        }
    }

    fun setIsAdHostsUpdateTime(time: Long) {
        sharedPreferences.edit().let {
            it.putLong(HOSTS_UPDATE, time)
            it.apply()
        }
    }

    fun getAdHostsUpdateTime(): Long {
        return sharedPreferences.getLong(HOSTS_UPDATE, 0)
    }

    fun getIsPopulated(): Boolean {
        return sharedPreferences.getBoolean(HOSTS_POPULATED, false)
    }

    fun setIsPopulated(isPopulated: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(HOSTS_POPULATED, isPopulated)
            it.apply()
        }
    }

    fun getIsExternalUse(): Boolean {
        val defIsExternal = FileUtil.isExternalStorageWritable()

        return sharedPreferences.getBoolean(IS_EXTERNAL_USE, defIsExternal)
    }

    fun setIsExternalUse(isExternalUse: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_EXTERNAL_USE, isExternalUse)
            it.apply()
        }
    }

    fun getIsAppDirUse(): Boolean {
        return sharedPreferences.getBoolean(IS_APP_DIR_USE, false)
    }

    fun setIsAppDirUse(isAppDirUse: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_APP_DIR_USE, isAppDirUse)
            it.apply()
        }
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(
            IS_DARK_MODE,
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        )
    }

    fun setIsDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_DARK_MODE, isDarkMode)
            it.apply()
        }
    }

    fun getRegularDownloaderThreadCount(): Int {
        return sharedPreferences.getInt(REGULAR_THREAD_COUNT, 1)
    }

    fun setRegularDownloaderThreadCount(count: Int) {
        sharedPreferences.edit().let {
            it.putInt(REGULAR_THREAD_COUNT, count)
            it.apply()
        }
    }

    fun getM3u8DownloaderThreadCount(): Int {
        return sharedPreferences.getInt(M3U8_THREAD_COUNT, 3) // means 4
    }

    fun setM3u8DownloaderThreadCount(count: Int) {
        sharedPreferences.edit().let {
            it.putInt(M3U8_THREAD_COUNT, count)
            it.apply()
        }
    }

    fun getVideoDetectionTreshold(): Int {
        return sharedPreferences.getInt(VIDEO_DETECTION_TRESHOLD, 5 * 1024 * 1024)
    }

    fun setVideoDetectionTreshold(count: Int) {
        sharedPreferences.edit().let {
            it.putInt(VIDEO_DETECTION_TRESHOLD, count)
            it.apply()
        }
    }

    fun getIsLockPortrait(): Boolean {
        return sharedPreferences.getBoolean(IS_LOCK_PORTRAIT, false)
    }

    fun setIsLockPortrait(isLock: Boolean) {
        sharedPreferences.edit().let {
            it.putBoolean(IS_LOCK_PORTRAIT, isLock)
            it.apply()
        }
    }
}