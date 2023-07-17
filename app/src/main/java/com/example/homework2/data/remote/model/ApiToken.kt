package com.example.homework2.data.remote.model

@kotlinx.serialization.Serializable
data class ApiToken(
    val token: String,
    val userId : String,
)