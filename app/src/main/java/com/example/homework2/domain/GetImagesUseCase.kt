package com.example.homework2.domain

import androidx.paging.PagingData
import com.example.homework2.domain.model.Image
import com.example.homework2.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(username : String) : Flow<PagingData<Image>> {
        return repository.getImages(username)
    }


}