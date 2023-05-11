package com.example.homework2.data.model

data class Image(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: String,
    val sizes: Array<ImageSize>,
)