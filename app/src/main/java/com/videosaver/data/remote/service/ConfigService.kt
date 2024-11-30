package com.videosaver.data.remote.service

import com.videosaver.data.local.room.entity.SupportedPage
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ConfigService {

    @GET("supported_pages.json")
    fun getSupportedPages(): Flowable<List<SupportedPage>>
}