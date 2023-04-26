package com.example.homework2.presentation.postViewCard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.homework2.databinding.ActivityPostBinding
import com.example.homework2.data.DataProfile

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    companion object {
        fun createIntent(context: Context, data: DataProfile) =
            Intent(context, PostActivity::class.java).apply {
                putExtra("ARG_TEXT_KEY", data)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        val data = intent.extras?.getParcelable<DataProfile>("ARG_TEXT_KEY")
        with(binding) {
            textViewName.text = data?.name
            textViewDate.text = data?.date
            imageViewPost.load(data?.link)
            textViewPost.text = data?.title
        }
        setContentView(binding.root)
    }
}