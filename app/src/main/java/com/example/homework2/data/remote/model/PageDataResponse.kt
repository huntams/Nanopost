package com.example.homework2.data.remote.model



@kotlinx.serialization.Serializable
data class PageDataResponse<T>(
    val count: Int,
    val total: Int,
    val offset: String? = null,
    val items: List<T>,
)