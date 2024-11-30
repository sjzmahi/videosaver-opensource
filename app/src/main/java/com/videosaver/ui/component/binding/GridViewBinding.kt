package com.videosaver.ui.component.binding

import android.widget.GridView
import androidx.databinding.BindingAdapter
import com.videosaver.data.local.room.entity.PageInfo
import com.videosaver.ui.component.adapter.*

object GridViewBinding {
    @BindingAdapter("app:items")
    @JvmStatic
    fun GridView.setTopPages(items: List<PageInfo>) {
        with(adapter as TopPageAdapter?) {
            this?.let { setData(items) }
        }
    }
}