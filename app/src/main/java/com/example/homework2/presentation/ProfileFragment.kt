package com.example.homework2.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.data.DataImages
import com.example.homework2.data.DataProfile
import com.example.homework2.data.remote.ResultLoader
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.databinding.ProfileFragmentBinding
import com.example.homework2.presentation.imagesCard.DataImagesCard
import com.example.homework2.presentation.imagesCard.ImageActivity
import com.example.homework2.presentation.imagesCard.ImagesCardAdapter
import com.example.homework2.presentation.postViewCard.PostActivity
import com.example.homework2.presentation.postViewCard.PostAdapter
import com.example.homework2.presentation.postViewCard.PostPagingAdapter
import com.example.homework2.presentation.profile.ProfileAdapter
import com.example.homework2.presentation.profile.ProfileViewModel
import javax.inject.Inject
import kotlin.random.Random

class ProfileFragment : Fragment() {
    private fun createLink() = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
            Random.nextInt(1, 700).toString().padStart(3, '0') +
            ".png"

    private val binding by viewBinding(ProfileFragmentBinding:: bind)

    @Inject
    lateinit var profAdapter: ProfileAdapter

    @Inject
    lateinit var postAdapter: PostAdapter
    @Inject
    lateinit var postPagingAdapter: PostPagingAdapter

    @Inject
    lateinit var imageAdapter: ImagesCardAdapter

    private val dataList = listOf(

        DataProfile(
            "rand1", "subrand2", date = "test",
            link = createLink()
        )
    )
    private val viewModel by viewModels<FeedViewModel>()
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

        viewModel.postLiveData.observe(viewLifecycleOwner) {
            postPagingAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
        imageAdapter.apply {
            setCallback {
                //startActivity(ImageActivity.createIntent(this@MainActivity, dataImage))
            }
            submitList(listOf(dataListImage))
        }
        postAdapter.apply {
            setCallback {
                //startActivity(PostActivity.createIntent(, dataList[0]))
            }
            submitList(dataList.toList())
        }
        binding.recyclerView.adapter = ConcatAdapter(profAdapter, imageAdapter, postAdapter)
        with(viewModel){
            getProfile()
            postLiveData.observe(viewLifecycleOwner){result->
                when(result){
                    is ResultLoader.Failure-> TODO()

                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}