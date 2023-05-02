package com.example.homework2.presentation.imagesCard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.databinding.ActivityImageBinding
import com.example.homework2.databinding.ActivityPostBinding
import com.example.homework2.presentation.ImagesAdapter
import kotlin.random.Random

class ImageFragment : Fragment(R.layout.activity_image) {
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
    private val binding by viewBinding(ActivityImageBinding:: bind)

    companion object {
        fun createIntent(context: Context, data: ArrayList<DataImagesCard>) =
            Intent(context, ImageActivity::class.java).apply {
                putExtra("ARG_LINK_KEY", data)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        val data = intent.extras?.getParcelableArrayList<DataImagesCard>("ARG_LINK_KEY")
        with(binding) {
            imageAdapter.submitList(data)
            recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                adapter = imageAdapter
            }
        }

         */
    }
}