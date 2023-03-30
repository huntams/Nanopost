package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.databinding.ProfileViewCardBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding){
            profViewCard.apply {
                setTitle("gsd")
                setDate("tewst")
            }
        }
    }
}