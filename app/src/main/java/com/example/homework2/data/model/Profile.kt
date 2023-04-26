package com.example.homework2.data.model


data class Profile(
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