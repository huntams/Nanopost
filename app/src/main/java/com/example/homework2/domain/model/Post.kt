package com.example.homework2.domain.model


data class Post(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: Long,
    val text: String? = null,
    val images : List<Image>,
    val likes : Like,
)