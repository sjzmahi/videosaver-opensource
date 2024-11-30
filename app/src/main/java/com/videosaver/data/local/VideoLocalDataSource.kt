package com.videosaver.data.local

import com.videosaver.data.local.room.dao.VideoDao
import com.videosaver.data.local.room.entity.VideoInfo
import com.videosaver.data.repository.VideoRepository
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoLocalDataSource @Inject constructor(
    private val videoDao: VideoDao
) : VideoRepository {

    override fun getVideoInfo(url: Request, isM3u8OrMpd: Boolean): VideoInfo {
        return videoDao.getVideoById(url.url.toString()).toSingle().blockingGet()
    }

    override fun saveVideoInfo(videoInfo: VideoInfo) {
        videoDao.insertVideo(videoInfo)
    }

}