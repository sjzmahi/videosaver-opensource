package com.videosaver.ui.component.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.videosaver.data.local.model.LocalVideo
import com.videosaver.data.local.model.Proxy
import com.videosaver.data.local.model.Suggestion
import com.videosaver.data.local.room.entity.HistoryItem
import com.videosaver.data.local.room.entity.PageInfo
import com.videosaver.data.local.room.entity.ProgressInfo
import com.videosaver.data.local.room.entity.VideoInfo
import com.videosaver.ui.component.adapter.*
import com.videosaver.ui.main.home.browser.webTab.WebTab

object RecyclerViewBinding {
    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setWebTabs(tabs: List<WebTab>) {
        with(adapter as WebTabsAdapter?) {
            this?.let { setData(tabs) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setTopPages(items: List<PageInfo>) {
        with(adapter as TopPageAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setSuggestions(items: List<Suggestion>) {
        with(adapter as SuggestionAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setProgressInfos(items: List<ProgressInfo>) {
        with(adapter as ProgressAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setProxiesList(items: List<Proxy>) {
        with(adapter as ProxiesAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setVideoInfos(items: List<LocalVideo>) {
        with(adapter as VideoAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.historyItems(items: List<HistoryItem>) {

        if (adapter is HistoryAdapter?) {
            with(adapter as HistoryAdapter?) {
                this?.let { setData(items) }
            }
        }
        if (adapter is HistorySearchAdapter?) {
            with(adapter as HistorySearchAdapter?) {
                this?.let { setData(items) }
            }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setDetectedVideoInfos(items: List<VideoInfo>) {
        with(adapter as VideoInfoAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setDetectedVideoInfosSet(items: Set<VideoInfo>) {
        with(adapter as VideoInfoAdapter?) {
            this?.let { setData(items.toList()) }
        }
    }
}