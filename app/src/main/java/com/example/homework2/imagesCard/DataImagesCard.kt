package com.example.homework2.imagesCard

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DataImagesCard(
    val link: String,
    val id: String = UUID.randomUUID().toString(),
) : Parcelable