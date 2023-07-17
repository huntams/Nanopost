package com.example.homework2.data.remote.model

@kotlinx.serialization.Serializable
data class RegistrationRequest(
    val username : String,
    val password : String,
)