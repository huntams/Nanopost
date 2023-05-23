package com.example.homework2.domain


import com.example.homework2.data.remote.model.ApiResultResponse
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class SubscribeUseCase @Inject constructor(

    private val repository: ProfileRepository
) {

    suspend fun execute(username: String) : ApiResultResponse {
        return repository.subscribe(username)
    }
}