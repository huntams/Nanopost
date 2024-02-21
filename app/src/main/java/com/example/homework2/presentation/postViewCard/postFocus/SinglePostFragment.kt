package com.example.homework2.presentation.postViewCard.postFocus

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.homework2.R
import com.example.homework2.databinding.FragmentSinglePostBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class SinglePostFragment : Fragment(R.layout.fragment_single_post) {
    private val menuHost: MenuHost by lazy { requireActivity() }
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
                    SimpleDateFormat("d MMMM,yyyy H:mm:s").format(Date(post.dateCreated))
                imageViewRounded.load(post.owner.avatarUrl)
                textViewPostText.text = post.text
                singlePostAdapter.apply {
                    setCallback {
                        val bundle = Bundle()
                        bundle.putString("ImageId", it.id)
                        findNavController().navigate(
                            R.id.singleImageFragment,bundle
                        )
                    }
                    submitList(post.images)
                }
                textViewFavoriteNumber.text = post.likes.likesCount.toString()
                recyclerView.adapter = singlePostAdapter
            }

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
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}