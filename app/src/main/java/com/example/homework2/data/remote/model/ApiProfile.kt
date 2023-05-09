package com.example.homework2.data.remote.model


@kotlinx.serialization.Serializable
data class ApiProfile(
    val id: String,
    val username: String,
    val displayName : String?,
    val bio: String?,
    val avatarId: String?,
    val avatarSmall: String?,
    val avatarLarge: String?,
    val subscribed: String,
    val subscribersCount: String,
    val postsCount: String,
    val imagesCount: String,
    val images: String,
)