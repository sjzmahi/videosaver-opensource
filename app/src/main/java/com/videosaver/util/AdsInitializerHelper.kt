package com.videosaver.util

import android.widget.Toast
import com.videosaver.data.repository.AdBlockHostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant
import java.util.Date

class AdsInitializerHelper {
    companion object {
        private const val ADS_LIST_UPDATE_TIME_DAYS = 7

        fun initializeAdBlocker(
            adBlockHostsRepository: AdBlockHostsRepository,
            sharedPrefHelper: SharedPrefHelper,
            lifecycleScope: CoroutineScope
        ) {
            var handle: DisposableHandle? = null
            handle = lifecycleScope.launch(Dispatchers.IO) {
                val isAdBlockerOn = sharedPrefHelper.getIsAdBlocker()
                if (isAdBlockerOn) {
                    val cachedCount = adBlockHostsRepository.getCachedCount()
                    if (cachedCount > 0) {
                        return@launch
                    }
                    val lastUpdateTime = sharedPrefHelper.getAdHostsUpdateTime()
                    val currentTime = System.currentTimeMillis()
                    val duration = Duration.between(
                        Instant.ofEpochMilli(lastUpdateTime),
                        Instant.ofEpochMilli(currentTime)
                    )
                    val isOutdated = duration.toDays() > ADS_LIST_UPDATE_TIME_DAYS

                    var isUpdated = false
                    val isInitialized: Boolean

                    if (isOutdated) {
                        AppLogger.d("HOST LIST OUTDATED, UPDATING...")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                ContextUtils.getApplicationContext(),
                                "AdBlock hosts lists start updating...",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        isInitialized = adBlockHostsRepository.initialize(true)
                        if (isInitialized) {
                            sharedPrefHelper.setIsAdHostsUpdateTime(Date().time)
                            isUpdated = true
                            AppLogger.d("HOST LISTS UPDATED DONE, TIME: ${Date()}")
                        } else {
                            AppLogger.d("HOST LISTS UPDATED FAIL, TIME: ${Date()}")
                            isUpdated = false
                        }
                    } else {
                        isInitialized = adBlockHostsRepository.initialize(false)

                        AppLogger.d("HOST LISTS INITIALIZED DONE, TIME: ${Date()}")
                    }

                    withContext(Dispatchers.Main) {
                        when {
                            // Means initialized and updated
                            isInitialized && !isUpdated -> {
                                Toast.makeText(
                                    ContextUtils.getApplicationContext(),
                                    "AdBlock hosts lists initialized",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            // Initialization Failed
                            !isInitialized -> {
                                Toast.makeText(
                                    ContextUtils.getApplicationContext(),
                                    "AdBlock hosts lists initialized failed",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            // Means initialized only
                            else -> {
                                Toast.makeText(
                                    ContextUtils.getApplicationContext(),
                                    "AdBlock hosts lists initialized and updated",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }.invokeOnCompletion {
                handle?.dispose()
            }
        }
    }
}