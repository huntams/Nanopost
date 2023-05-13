package com.example.homework2.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.databinding.FragmentAddPostBinding
import com.example.homework2.presentation.profile.ProfileViewModel
import com.example.homework2.service.CreatePostService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostAddFragment : Fragment(){

    private val binding by viewBinding(FragmentAddPostBinding:: bind)
    private val viewModel by viewModels<CreatePostService>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.createPostUseCase.execute(text = null,)
        super.onViewCreated(view, savedInstanceState)
    }
}