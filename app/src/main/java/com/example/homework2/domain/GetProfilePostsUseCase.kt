package com.example.homework2.domain

import androidx.paging.PagingData
import com.example.homework2.data.model.Post
import com.example.homework2.data.remote.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilePostsUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(username : String) : Flow<PagingData<Post>> {
        return repository.getProfilePosts(username)
    }


}