package com.example.homework2.data.remote.model

@kotlinx.serialization.Serializable
 data class ApiImage(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val sizes: List<ApiImageSize>,
)