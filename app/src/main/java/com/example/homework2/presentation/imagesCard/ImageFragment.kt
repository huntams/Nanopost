package com.example.homework2.presentation.imagesCard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.databinding.FragmentPagingImagesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_paging_images) {

    private val binding by viewBinding(FragmentPagingImagesBinding::bind)

    @Inject
    lateinit var imagePagingAdapter: ImagePagingAdapter

    private val viewModel by viewModels<ImageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.getImages("evo")
            viewModel.imagesLiveData.observe(viewLifecycleOwner) {
                imagePagingAdapter.apply {
                    setCallback {
                    }

                    submitData(viewLifecycleOwner.lifecycle,it)
                }
            }
            recyclerview.apply {
                layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                adapter = imagePagingAdapter
            }
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
}