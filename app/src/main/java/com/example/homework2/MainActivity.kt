package com.example.homework2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.databinding.ProfileViewCardBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            profViewCard.apply {
                setTitle("gsd")
                setDate("tewst")
                setImage(R.drawable.image_placeholder)
                setButtonColor(R.color.md_theme_dark_surfaceVariant)
                setImageNumber("1")
                setPostNumber("3213")
                setSubsNumber("123")
            }
        }
    }
}