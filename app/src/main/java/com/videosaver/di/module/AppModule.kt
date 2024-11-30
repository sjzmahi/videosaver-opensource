package com.videosaver.di.module

import android.app.Application
import android.content.Context
import com.videosaver.MyApp
import com.videosaver.di.qualifier.ApplicationContext
import com.videosaver.util.scheduler.BaseSchedulers
import com.videosaver.util.scheduler.BaseSchedulersImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: MyApp): Context

    @Binds
    abstract fun bindApplication(application: MyApp): Application

    @Singleton
    @Binds
    abstract fun bindBaseSchedulers(baseSchedulers: BaseSchedulersImpl): BaseSchedulers
}