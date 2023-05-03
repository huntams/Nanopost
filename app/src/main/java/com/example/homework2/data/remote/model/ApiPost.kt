package com.example.homework2.data.remote.model


@kotlinx.serialization.Serializable
data class ApiPost(
    val id: String,
    val text: String,
)