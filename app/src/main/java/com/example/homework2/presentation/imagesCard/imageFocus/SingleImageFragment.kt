package com.example.homework2.presentation.imagesCard.imageFocus

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_images, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId) {
            findNavController().popBackStack()
        }

        if (item.itemId == R.id.actionDelete) {
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
        return super.onOptionsItemSelected(item)
    }
}