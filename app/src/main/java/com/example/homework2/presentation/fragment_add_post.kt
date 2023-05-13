package com.example.homework2.presentation

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

private val pickMedia =
    registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriImage ->
        if (uriImage != null) {
            with(binding) {

                closeAddImageView.setImageURI(uriImage)
                uri = closeAddImageView.drawToBitmap().convertToByteArray()
                vision(View.VISIBLE, View.INVISIBLE)
            }
        } else {
            Toast.makeText(context, "Изображение не было выбрано", Toast.LENGTH_SHORT).show()
        }
    }
