package com.example.homework2.data.model

import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiLike


data class Post(
    val id: String,
    val text: String?,
    val images: List<ApiImage>,
    val dateCreated: Long?,
    val likes: ApiLike,
)