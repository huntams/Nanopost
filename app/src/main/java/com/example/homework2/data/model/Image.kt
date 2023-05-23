package com.example.homework2.data.model

import com.example.homework2.data.remote.model.ApiImageSize
import com.example.homework2.data.remote.model.ApiProfileCompact

data class Image(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val sizes: List<ApiImageSize>,
)