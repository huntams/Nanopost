package com.example.homework2.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.homework2.data.mappers.PostMapper
import com.example.homework2.data.mappers.ProfileMapper
import com.example.homework2.data.model.Profile
import com.example.homework2.data.pagging.PostPagingSource
import com.example.homework2.data.remote.NanopostApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: NanopostApiService,
    private val profileMapper: ProfileMapper,
    private val postMapper: PostMapper,
) : ProfileRepository {

    override suspend fun getProfile(profileId: String): Profile {
        return profileMapper.apiToModel(apiService.getProfile(profileId))
    }

    override suspend fun getProfilePosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(apiService) },
        ).flow.map { pagingdata ->
            pagingdata.map {
                postMapper.apiToModel(apiService.getProfilePosts(profileId = it.id, count = it.count, offset = it.offset))
            }

        }
    }
}