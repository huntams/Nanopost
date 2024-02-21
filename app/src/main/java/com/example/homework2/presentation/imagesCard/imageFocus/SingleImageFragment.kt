package com.example.homework2.presentation.imagesCard.imageFocus

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.FragmentSingleImageBinding
import com.example.homework2.presentation.imagesCard.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class SingleImageFragment : Fragment(R.layout.fragment_single_image) {

    private val binding by viewBinding(FragmentSingleImageBinding::bind)
    private val menuHost: MenuHost by lazy { requireActivity() }

    @Inject
    lateinit var prefs: PrefsStorage

    private val viewModel by viewModels<ImageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel.getImage(arguments?.getString("ImageId").toString())
            viewModel.imageLiveData.observe(viewLifecycleOwner) { image ->
                textViewName.text = image.owner.username
                textViewDate.text =
                    SimpleDateFormat("d MMMM,yyyy H:mm:s").format(Date(image.dateCreated))
                imageViewRounded.load(image.owner.avatarUrl)
                imageViewItemPost.load(image.sizes[0].url)
            }
        }
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.navigation_images, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionDelete -> {
                        viewModel.imageLiveData.observe(viewLifecycleOwner) { image ->
                            if (image.owner.username == prefs.username)
                                viewModel.deleteImage(arguments?.getString("ImageId").toString())
                            else
                                Toast.makeText(
                                    requireContext(),
                                    "Вы не можете удалять изображения чужих профилей",
                                    Toast.LENGTH_LONG
                                ).show()
                        }
                        findNavController().popBackStack()
                    }
                    else -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        super.onViewCreated(view, savedInstanceState)
    }
}