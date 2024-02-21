package com.example.homework2.presentation.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.FragmentEditProfileBinding
import com.example.homework2.domain.GetContentUriUseCase
import dagger.hilt.android.AndroidEntryPoint
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
                viewModel.editProfile(
                    profileId = prefs.username.toString(),
                    displayName = editTextUsername.text.toString(),
                    bio = editTextBio.text.toString(),
                    avatar = avatar?.let { it1 -> getContentUriUseCase(it1) }
                )
            }
        }


    }
}