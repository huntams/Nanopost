package com.example.homework2.data.model

@kotlinx.serialization.Serializable
data class Post(
    val id: String,
    val text: String,
)