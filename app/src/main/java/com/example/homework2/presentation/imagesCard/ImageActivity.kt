package com.example.homework2.presentation.imagesCard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.homework2.databinding.ActivityImageBinding
import com.example.homework2.presentation.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random
@AndroidEntryPoint
class ImageActivity : AppCompatActivity() {

    private val dataList = mutableListOf<DataImagesCard>().apply {
        repeat(20) {
            add(
                DataImagesCard(
                    link = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
                            Random.nextInt(0, 700).toString().padStart(3, '0') +
                            ".png"
                )
            )
        }
    }
    private val imageAdapter by lazy {
        ImagesAdapter()
    }
    private lateinit var binding: ActivityImageBinding

    companion object {
        fun createIntent(context: Context, data: ArrayList<DataImagesCard>) =
            Intent(context, ImageActivity::class.java).apply {
                putExtra("ARG_LINK_KEY", data)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        val data = intent.extras?.getParcelableArrayList<DataImagesCard>("ARG_LINK_KEY")
        with(binding) {
            imageAdapter.submitList(data)
            recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                adapter = imageAdapter
            }
            setContentView(binding.root)
        }
    }
}