package com.videosaver.di.module

import androidx.work.WorkerFactory
import com.videosaver.data.repository.ProgressRepository
import com.videosaver.util.FileUtil
import com.videosaver.util.NotificationsHelper
import com.videosaver.util.SharedPrefHelper
import com.videosaver.util.downloaders.generic_downloader.DaggerWorkerFactory
import com.videosaver.util.proxy_utils.CustomProxyController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MyWorkerModule {
    @Provides
    @Singleton
    fun workerFactory(
        progressRepository: ProgressRepository,
        fileUtil: FileUtil,
        notificationsHelper: NotificationsHelper,
        proxyController: CustomProxyController,
        sharedPrefHelper: SharedPrefHelper
    ): WorkerFactory {
        return DaggerWorkerFactory(
            progressRepository,
            fileUtil,
            notificationsHelper,
            proxyController,
            sharedPrefHelper
        )
    }
}

