package com.example.homework2.domain

import com.example.homework2.domain.model.Post
import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(postId : String) : Post {
        return repository.getPost(postId)
    }
}