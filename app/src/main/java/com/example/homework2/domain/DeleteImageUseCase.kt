package com.example.homework2.domain

import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiResultResponse
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val repository: ProfileRepository
){
    suspend fun execute(imageId : String) : ApiResultResponse {
        return repository.deleteImage(imageId)
    }
}