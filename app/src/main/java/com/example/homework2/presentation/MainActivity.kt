package com.example.homework2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.data.DataImages
import com.example.homework2.presentation.imagesCard.DataImagesCard
import com.example.homework2.presentation.imagesCard.ImageActivity
import com.example.homework2.presentation.postViewCard.PostActivity
import com.example.homework2.presentation.postViewCard.PostAdapter
import com.example.homework2.data.DataProfile
import com.example.homework2.presentation.imagesCard.ImagesCardAdapter
import com.example.homework2.presentation.profile.ProfileAdapter
import com.example.homework2.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private fun createLink() = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
            Random.nextInt(1, 700).toString().padStart(3, '0') +
            ".png"

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var profAdapter: ProfileAdapter

    @Inject
    lateinit var postAdapter: PostAdapter

    @Inject
    lateinit var imageAdapter: ImagesCardAdapter

    private val dataList = listOf(

        DataProfile(
            "rand1", "subrand2", date = "test",
            link = createLink()
        )
    )
    private val viewModel by viewModels<ProfileViewModel>()
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
        viewModel.getProfile()
        viewModel.profileLiveData.observe(this) {
            profAdapter.submitList(mutableListOf(it))
        }
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