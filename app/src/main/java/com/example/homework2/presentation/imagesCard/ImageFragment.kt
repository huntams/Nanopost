package com.example.homework2.presentation.imagesCard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.FragmentPagingImagesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_paging_images) {

    private val binding by viewBinding(FragmentPagingImagesBinding::bind)
    private val menuHost: MenuHost by lazy { requireActivity() }
    @Inject
    lateinit var prefs: PrefsStorage

    @Inject
    lateinit var imagePagingAdapter: ImagePagingAdapter

    private val viewModel by viewModels<ImageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.getImages(prefs.profile.toString())
            viewModel.imagesLiveData.observe(viewLifecycleOwner) {
                imagePagingAdapter.apply {
                    setCallback {
                        val bundle = Bundle()
                        bundle.putString("ImageId", it.id)
                        findNavController().navigate(
                            R.id.singleImageFragment, bundle
                        )
                    }

                    submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
            recyclerview.apply {
                layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                adapter = imagePagingAdapter
            }
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.empty_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        else -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

}