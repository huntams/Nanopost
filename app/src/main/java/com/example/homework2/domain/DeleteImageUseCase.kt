package com.example.homework2.domain

import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val repository: ProfileRepository
){
    suspend fun execute(imageId : String) : Boolean {
        return repository.deleteImage(imageId)
    }
}