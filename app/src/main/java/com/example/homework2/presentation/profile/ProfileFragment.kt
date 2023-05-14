package com.example.homework2.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.homework2.R
import com.example.homework2.data.DataImages
import com.example.homework2.data.DataProfile
import com.example.homework2.data.model.Profile
import com.example.homework2.databinding.FragmentProfileBinding
import com.example.homework2.presentation.imagesCard.DataImagesCard
import com.example.homework2.presentation.imagesCard.ImagesCardAdapter
import com.example.homework2.presentation.postViewCard.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private fun createLink() = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
            Random.nextInt(1, 700).toString().padStart(3, '0') +
            ".png"

    private val profiles = mutableListOf<Profile>(

    )
    private val binding by viewBinding(FragmentProfileBinding:: bind)

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

        viewModel.getProfile("huntams")
        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            profiles.add(it)
            profAdapter.submitList(profiles)
        //profAdapter.submitList(mutableListOf(it))
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
        binding.recyclerView.adapter = ConcatAdapter(profAdapter,imageAdapter, postAdapter)

        super.onViewCreated(view, savedInstanceState)
        /*
        val navGraph = findNavController().navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.profileFragment)
        findNavController().graph = navGraph
         */
    }
}