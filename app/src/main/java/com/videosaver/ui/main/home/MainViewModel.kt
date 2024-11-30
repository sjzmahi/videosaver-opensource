package com.videosaver.ui.main.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
//import com.allVideoDownloaderXmaster.OpenForTesting
import com.videosaver.data.local.room.entity.VideoInfo
import com.videosaver.ui.main.base.BaseViewModel
import com.videosaver.ui.main.home.browser.BrowserServicesProvider
import com.videosaver.util.SingleLiveEvent
import javax.inject.Inject

//@OpenForTesting
class MainViewModel @Inject constructor() :
    BaseViewModel() {

    var browserServicesProvider: BrowserServicesProvider? = null

    val openedUrl = ObservableField<String?>()

    val openedText = ObservableField<String?>()

    val isBrowserCurrent = ObservableBoolean(false)

    val currentItem = ObservableField<Int>()

    val offScreenPageLimit = ObservableField(4)

    // pair - format:url
    val selectedFormatTitle = ObservableField<Pair<String, String>?>()

    val currentOriginal = ObservableField<String>()

    val downloadVideoEvent = SingleLiveEvent<VideoInfo>()

    val openDownloadedVideoEvent = SingleLiveEvent<String>()

    val openNavDrawerEvent = SingleLiveEvent<Unit?>()

    override fun start() {
    }

    override fun stop() {
    }
}
