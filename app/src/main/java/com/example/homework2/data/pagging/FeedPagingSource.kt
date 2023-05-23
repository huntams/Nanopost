package com.example.homework2.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.model.ApiPost

class FeedPagingSource(
    private val apiService: NanopostApiService,
) : PagingSource<String, ApiPost>() {

    override fun getRefreshKey(state: PagingState<String, ApiPost>): String? {
        return null

    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ApiPost> {
        try {
            val response = apiService.getFeed(
                count = params.loadSize,
                offset = params.key
            )
            return LoadResult.Page(
                data = response.items,
                nextKey = response.offset,
                prevKey = null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}