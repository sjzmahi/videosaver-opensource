package com.videosaver.di.module

import com.videosaver.di.ActivityScoped
import com.videosaver.ui.main.home.MainActivity
import com.videosaver.di.module.activity.MainModule
import com.videosaver.ui.main.player.VideoPlayerActivity
import com.videosaver.di.module.activity.VideoPlayerModule
import com.videosaver.ui.main.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [VideoPlayerModule::class])
    internal abstract fun bindVideoPlayerActivity(): VideoPlayerActivity
}