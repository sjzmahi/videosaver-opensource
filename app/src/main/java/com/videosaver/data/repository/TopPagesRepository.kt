package com.videosaver.data.repository

import com.videosaver.data.local.room.entity.PageInfo
import com.videosaver.di.qualifier.LocalData
import com.videosaver.di.qualifier.RemoteData
import com.videosaver.util.FaviconUtils
import com.videosaver.util.proxy_utils.OkHttpProxyClient
import javax.inject.Inject
import javax.inject.Singleton

interface TopPagesRepository {
    suspend fun getTopPages(): List<PageInfo>

    fun saveTopPage(pageInfo: PageInfo)

    fun deletePageInfo(pageInfo: PageInfo)

    suspend fun updateLocalStorage()
}

@Singleton
class TopPagesRepositoryImpl @Inject constructor(
    @LocalData private val localDataSource: TopPagesRepository,
    @RemoteData private val remoteDataSource: TopPagesRepository,
    private val okHttpClient: OkHttpProxyClient
) : TopPagesRepository {

    override suspend fun getTopPages(): List<PageInfo> {
        return localDataSource.getTopPages()
    }

    override fun saveTopPage(pageInfo: PageInfo) {
        localDataSource.saveTopPage(pageInfo)
    }

    override fun deletePageInfo(pageInfo: PageInfo) {
        localDataSource.deletePageInfo(pageInfo)
    }

    override suspend fun updateLocalStorage() {
        val pages = localDataSource.getTopPages()
        for (page in pages) {
            val bitmap = try {
                FaviconUtils.getEncodedFaviconFromUrl(
                    okHttpClient.getProxyOkHttpClient(),
                    page.link
                )
            } catch (e: Throwable) {
                null
            }
            localDataSource.saveTopPage(page)
            val bitmapBytes = try {
                FaviconUtils.bitmapToBytes(bitmap)
            } catch (e: Throwable) {
                null
            }
            page.favicon = bitmapBytes
            localDataSource.saveTopPage(page)
        }
    }
}