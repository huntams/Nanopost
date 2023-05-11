package com.example.homework2.data.remote.model


@kotlinx.serialization.Serializable
data class ApiImageSize(
    val width : Int,
    val height : Int,
    val url : String,
)
