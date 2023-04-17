package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.imagesCard.DataImages
import com.example.homework2.imagesCard.DataImagesCard
import com.example.homework2.imagesCard.ImageActivity
import com.example.homework2.postViewCard.PostActivity
import com.example.homework2.postViewCard.PostAdapter
import com.example.homework2.profile.DataProfile
import com.example.homework2.profile.ProfileAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private fun createLink() = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
            Random.nextInt(1, 700).toString().padStart(3, '0') +
            ".png"

    private lateinit var binding: ActivityMainBinding
    private val profAdapter by lazy {
        ProfileAdapter()
    }
    private val imageAdapter by lazy {
        ImagesCardAdapter()
    }
    private val postAdapter by lazy {
        PostAdapter()
    }
    private val dataList = listOf(

        DataProfile(
            "rand1", "subrand2", date = "test",
            link = createLink()
        )
    )
    private val link = mutableListOf<String>()
    private val dataListImage =
        DataImages(
            link = link.apply {
                repeat(4) {
                    add(
                        createLink()
                    )
                }
            }
        )
    private val dataImage = ArrayList<DataImagesCard>().apply {
        repeat(dataListImage.link.size) {
            add(
                DataImagesCard(
                    link = dataListImage.link[it]
                )
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataImage.apply {
            repeat(10) {
                add(
                    DataImagesCard(
                        link = createLink()
                    )
                )
            }
        }
        profAdapter.submitList(dataList.toList())
        imageAdapter.apply {
            setCallback {
                startActivity(ImageActivity.createIntent(this@MainActivity, dataImage))
            }
            submitList(listOf(dataListImage))
        }
        postAdapter.apply {
            setCallback {
                startActivity(PostActivity.createIntent(this@MainActivity, dataList[0]))
            }
            submitList(dataList.toList())
        }
        binding.recyclerView.adapter = ConcatAdapter(profAdapter, imageAdapter, postAdapter)

    }


}