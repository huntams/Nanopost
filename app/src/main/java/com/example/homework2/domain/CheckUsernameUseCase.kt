package com.example.homework2.domain

import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class CheckUsernameUseCase @Inject constructor(

    private val repository: ProfileRepository
) {

    suspend fun execute(username: String) :  ApiResult {
        return repository.checkUsername(username)
    }
}