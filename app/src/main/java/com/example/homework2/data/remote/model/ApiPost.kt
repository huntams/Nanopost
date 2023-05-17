package com.example.homework2.data.remote.model

import com.example.homework2.data.model.Image


@kotlinx.serialization.Serializable
data class ApiPost(
    val id: String,
    val text: String?,
    val images : List<ApiImage>,
    val dateCreated: Int?,
    val likes : ApiLike,

)