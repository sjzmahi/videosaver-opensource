package com.videosaver.ui.main.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    abstract fun start()

    abstract fun stop()
}