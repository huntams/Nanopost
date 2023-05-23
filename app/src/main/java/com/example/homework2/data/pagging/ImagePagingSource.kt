package com.example.homework2.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiPost

class ImagePagingSource(
    private val apiService: NanopostApiService,
    private val username: String,
) : PagingSource<String, ApiImage>() {

    override fun getRefreshKey(state: PagingState<String, ApiImage>): String? {
        return null

    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ApiImage> {
        try {
            val response = apiService.getImages(
                profileId = username, count =
                params.loadSize,
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