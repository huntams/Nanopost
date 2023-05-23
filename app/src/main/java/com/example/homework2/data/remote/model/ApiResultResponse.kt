package com.example.homework2.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class ApiResultResponse(
    val result: Boolean
)