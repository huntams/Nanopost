package com.example.homework2.data.model

import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiLike
import com.example.homework2.data.remote.model.ApiProfileCompact


data class Post(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val text: String? = null,
    val images : List<ApiImage>,
    val likes : ApiLike,
)