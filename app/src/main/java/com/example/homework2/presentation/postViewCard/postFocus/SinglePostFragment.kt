package com.example.homework2.presentation.postViewCard.postFocus

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.homework2.R
import com.example.homework2.data.model.Post
import com.example.homework2.databinding.FragmentAddPostBinding
import com.example.homework2.databinding.FragmentSinglePostBinding
import com.example.homework2.presentation.profile.ProfileAdapter
import com.example.homework2.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class SinglePostFragment : Fragment(R.layout.fragment_single_post) {

    private val viewModel by viewModels<SinglePostViewModel>()
    private val binding by viewBinding(FragmentSinglePostBinding::bind)
    @Inject
    lateinit var singlePostAdapter: SinglePostAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getPost(arguments?.getString("PostId").toString())
        viewModel.postLiveData.observe(viewLifecycleOwner) {post->
            with(binding){
                textViewName.text = post.owner.username
                textViewDate.text =
                    SimpleDateFormat("MMMM d,yyyy H:mm:s").format(Date(post.dateCreated))
                imageViewRounded.load(post.owner.avatarUrl)
                textViewPostText.text = post.text
                singlePostAdapter.apply {
                    submitList(post.images)
                }
                textViewFavoriteNumber.text = post.likes.likesCount.toString()
                recyclerView.adapter = singlePostAdapter
            }

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}