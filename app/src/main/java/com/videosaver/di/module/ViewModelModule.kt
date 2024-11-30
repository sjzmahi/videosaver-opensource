package com.videosaver.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.videosaver.di.ViewModelKey
import com.videosaver.ui.main.history.HistoryViewModel
import com.videosaver.ui.main.home.browser.BrowserViewModel
import com.videosaver.ui.main.home.MainViewModel
import com.videosaver.ui.main.home.browser.homeTab.BrowserHomeViewModel
import com.videosaver.ui.main.home.browser.detectedVideos.VideoDetectionAlgVModel
import com.videosaver.ui.main.home.browser.webTab.WebTabViewModel
import com.videosaver.ui.main.home.browser.detectedVideos.DetectedVideosTabViewModel
import com.videosaver.ui.main.link.DownloadLinkViewModel
import com.videosaver.ui.main.player.VideoPlayerViewModel
import com.videosaver.ui.main.progress.ProgressViewModel
import com.videosaver.ui.main.proxies.ProxiesViewModel
import com.videosaver.ui.main.settings.SettingsViewModel
import com.videosaver.ui.main.splash.SplashViewModel
import com.videosaver.ui.main.video.VideoViewModel
import com.videosaver.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [AppModule::class])
abstract class ViewModelModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrowserViewModel::class)
    abstract fun bindBrowserViewModel(viewModel: BrowserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayerViewModel::class)
    abstract fun bindVideoPlayerViewModel(viewModel: VideoPlayerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProgressViewModel::class)
    abstract fun bindProgressViewModel(viewModel: ProgressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    abstract fun bindVideoViewModel(viewModel: VideoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloadLinkViewModel::class)
    abstract fun bindLinkViewModel(viewModel: DownloadLinkViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryViewModel(viewModel: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProxiesViewModel::class)
    abstract fun bindProxiesViewModel(viewModel: ProxiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WebTabViewModel::class)
    abstract fun bindWebTabViewModel(viewModel: WebTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrowserHomeViewModel::class)
    abstract fun bindBrowserHomeViewModel(viewModel: BrowserHomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoDetectionAlgVModel::class)
    abstract fun bindVideoDetectionAlgViewModel(viewModel: VideoDetectionAlgVModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetectedVideosTabViewModel::class)
    abstract fun bindVideoDetectionDetectedViewModel(viewModel: DetectedVideosTabViewModel): ViewModel
}
