package com.example.homework2.data.remote.model

import com.example.homework2.data.model.ImageSize
import com.example.homework2.data.model.ProfileCompact

@kotlinx.serialization.Serializable
data class ApiImage(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: String,
    val sizes: List<ApiImageSize>,
)