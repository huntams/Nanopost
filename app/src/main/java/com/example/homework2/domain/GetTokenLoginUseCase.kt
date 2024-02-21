package com.example.homework2.domain

import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class GetTokenLoginUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(username : String, password : String) : ApiToken {
        return repository.getToken(username,password)
    }
}