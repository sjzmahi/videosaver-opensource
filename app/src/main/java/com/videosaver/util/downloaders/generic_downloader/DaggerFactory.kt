package com.videosaver.util.downloaders.generic_downloader

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.videosaver.data.repository.ProgressRepository
import com.videosaver.util.FileUtil
import com.videosaver.util.NotificationsHelper
import com.videosaver.util.SharedPrefHelper
import com.videosaver.util.downloaders.generic_downloader.workers.GenericDownloadWorker
import com.videosaver.util.downloaders.generic_downloader.workers.GenericDownloadWorkerWrapper
import com.videosaver.util.proxy_utils.CustomProxyController
import javax.inject.Inject


class DaggerWorkerFactory @Inject constructor(
    private val progress: ProgressRepository,
    private val fileUtil: FileUtil,
    private val notificationsHelper: NotificationsHelper,
    private val proxyController: CustomProxyController,
    private val sharedPrefHelper: SharedPrefHelper
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context, workerClassName: String, workerParameters: WorkerParameters
    ): CoroutineWorker? {

        val workerKlass =
            Class.forName(workerClassName).asSubclass(GenericDownloadWorker::class.java)
        val constructor =
            workerKlass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is GenericDownloadWorkerWrapper -> {
                instance.sharedPrefHelper = sharedPrefHelper
                instance.progressRepository = progress
                instance.fileUtil = fileUtil
                instance.notificationsHelper = notificationsHelper
                instance.proxyController = proxyController
            }
        }

        return instance
    }
}