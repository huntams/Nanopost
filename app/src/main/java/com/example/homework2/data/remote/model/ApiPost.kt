package com.example.homework2.data.remote.model




@kotlinx.serialization.Serializable
data class ApiPost(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val text: String? = null,
    val images : List<ApiImage>,
    val likes : ApiLike,

)