package com.example.homework2.domain

import androidx.paging.PagingData
import com.example.homework2.data.model.Image
import com.example.homework2.data.model.ProfileCompact
import com.example.homework2.data.remote.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProfileCompactUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(query : String) : Flow<PagingData<ProfileCompact>> {
        return repository.searchProfile(query)
    }


}