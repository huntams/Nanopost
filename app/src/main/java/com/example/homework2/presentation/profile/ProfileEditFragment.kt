package com.example.homework2.presentation.profile

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.FragmentEditProfileBinding
import com.example.homework2.databinding.FragmentProfileBinding
import com.example.homework2.domain.GetContentUriUseCase
import com.example.homework2.presentation.service.CreatePostService
import com.example.homework2.presentation.service.EditProfileService
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEditFragment : Fragment(R.layout.fragment_edit_profile) {
    private val binding by viewBinding(FragmentEditProfileBinding::bind)

    @Inject
    lateinit var prefs: PrefsStorage
    @Inject
    lateinit var getContentUriUseCase: GetContentUriUseCase

    private var avatar: Uri? = null

    private val viewModel by viewModels<ProfileViewModel>()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriImage ->
            if (uriImage != null) {
                binding.imageViewRounded.setImageURI(uriImage)
                avatar = uriImage

                //uri = closeAddImageView.drawToBitmap().convertToByteArray()
                //vision(View.VISIBLE, View.INVISIBLE)
            } else {
                Toast.makeText(context, "Изображение не было выбрано", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imageViewRounded.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            buttonCancel.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonSave.setOnClickListener {
                /*
                activity?.startService(
                    EditProfileService.newIntent(
                        requireContext(),
                        displayName = editTextUsername.text.toString(),
                        bio = editTextBio.text.toString(),
                        avatar = avatar
                    )
                )
                */

                viewModel.editProfile(
                    profileId = prefs.username.toString(),
                    displayName = editTextUsername.text.toString(),
                    bio = editTextBio.text.toString(),
                    avatar = avatar?.let { it1 -> getContentUriUseCase(it1) }
                )


                //findNavController().popBackStack()
            }
        }


    }
}