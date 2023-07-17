package com.example.homework2.data.model

import com.example.homework2.data.remote.model.ApiImage


data class Profile(
    val id: String,
    val username: String,
    val displayName : String?,
    val bio: String?,
    val avatarId: String?,
    val avatarSmall: String?,
    val avatarLarge: String?,
    val subscribed: Boolean,
    val subscribersCount: Int,
    val postsCount: Int,
    val imagesCount: Int,
    val images: List<ApiImage>,
)