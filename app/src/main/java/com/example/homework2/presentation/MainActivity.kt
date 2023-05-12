package com.example.homework2.presentation


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding:: bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefsStorage = PrefsStorage(this)
        if(prefsStorage.token== null){
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(this))
            .build()
        Log.i("client",client.toString())

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}