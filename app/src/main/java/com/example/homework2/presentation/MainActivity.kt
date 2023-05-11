package com.example.homework2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.homework2.R
import com.example.homework2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private fun createLink() = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
            Random.nextInt(1, 700).toString().padStart(3, '0') +
            ".png"

    private val binding by viewBinding(ActivityMainBinding:: bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(this))
            .build()
        Log.i("client",client.toString())
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}