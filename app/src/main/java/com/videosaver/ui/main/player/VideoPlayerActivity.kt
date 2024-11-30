package com.videosaver.ui.main.player

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.videosaver.R
import com.videosaver.ui.main.base.BaseActivity
import com.videosaver.util.ext.addFragment

class VideoPlayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_player)

        intent.extras?.let { addFragment(R.id.content_frame, it, ::VideoPlayerFragment) }
    }
}