package com.example.homework2.domain

import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(postId : String) : Post {
        return repository.getPost(postId)
    }
}