package com.example.homework2.domain

import com.example.homework2.data.model.Image
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(imageId: String) : Image {
        return repository.getImage(imageId = imageId)
    }
}