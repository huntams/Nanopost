package com.example.homework2.data.remote

sealed class ResultLoader<T>(
) {
    class Loading<T> : ResultLoader<T>() {

    }

    @kotlinx.serialization.Serializable
    data class Success<T>(
        val value: T,
    ) : ResultLoader<T>()

    data class Failure<T>(
        val throwable: Throwable,
    ) : ResultLoader<T>()


}