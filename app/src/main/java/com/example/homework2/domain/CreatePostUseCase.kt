package com.example.homework2.domain

import com.example.homework2.data.model.Post
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: ProfileRepository
){
    suspend fun execute(text : String?, list: List<ByteArray>?) : Post {
        return repository.createPost(text= text, list = list)
    }
}