package com.videosaver.data.local.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.videosaver.data.local.room.entity.VideoInfo

data class VideoInfoWrapper(
    @SerializedName("info")
    @Expose
    var videoInfo: VideoInfo?
)