package com.example.homework2.data.remote.model


@kotlinx.serialization.Serializable
data class ApiProfileCompact(
    val id : String,
    val username : String,
    val displayName : String? = null,
    val avatarUrl : String? = null,
    val subscribed : Boolean,
)