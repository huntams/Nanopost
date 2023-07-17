package com.example.homework2.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiLike(
    val liked: Boolean,
    val likesCount: Int,
)