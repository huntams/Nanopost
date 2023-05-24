package com.example.homework2.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiProfileCompact
import retrofit2.http.Query

class ProfileCompactPagingSource(
    private val apiService: NanopostApiService,
    private val query: String,
) : PagingSource<String, ApiProfileCompact>() {

    override fun getRefreshKey(state: PagingState<String, ApiProfileCompact>): String? {
        return null

    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ApiProfileCompact> {
        try {
            val response = apiService.searchProfiles(
                query = query, count =
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