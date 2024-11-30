package com.videosaver.di.module.activity

import android.app.Activity
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.videosaver.di.ActivityScoped
import com.videosaver.di.FragmentScoped
import com.videosaver.ui.main.help.HelpFragment
import com.videosaver.ui.main.history.HistoryFragment
import com.videosaver.ui.main.home.browser.BrowserFragment
import com.videosaver.ui.main.home.MainActivity
import com.videosaver.ui.main.home.browser.homeTab.BrowserHomeFragment
import com.videosaver.ui.main.home.browser.webTab.WebTabFragment
import com.videosaver.ui.main.home.browser.detectedVideos.DetectedVideosTabFragment
import com.videosaver.ui.main.link.LinkFragment
import com.videosaver.ui.main.progress.ProgressFragment
import com.videosaver.ui.main.proxies.ProxiesFragment
import com.videosaver.ui.main.settings.SettingsFragment
import com.videosaver.ui.main.video.VideoFragment
import com.videosaver.util.fragment.FragmentFactory
import com.videosaver.util.fragment.FragmentFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @OptIn(UnstableApi::class)
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindBrowserFragment(): BrowserFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindProxiesFragment(): ProxiesFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindHistoryFragment(): HistoryFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindHelpFragment(): HelpFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindProgressFragment(): ProgressFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindVideoFragment(): VideoFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindSettingsFragment(): SettingsFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindWebTabFragment(): WebTabFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindDetectedVideosFragment(): DetectedVideosTabFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindBrowserHomeFragment(): BrowserHomeFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindLinkFragment(): LinkFragment

    @ActivityScoped
    @Binds
    abstract fun bindMainActivity(mainActivity: MainActivity): Activity

    @ActivityScoped
    @Binds
    abstract fun bindFragmentFactory(fragmentFactory: FragmentFactoryImpl): FragmentFactory
}