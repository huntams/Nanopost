package com.example.homework2.presentation.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    @Inject
    lateinit var prefs: PrefsStorage

    @Inject
    lateinit var postPagingAdapter: FeedPagingAdapter

    private val viewModel by viewModels<FeedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.getFeed()

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            postPagingAdapter.apply {
                submitData(viewLifecycleOwner.lifecycle, it)
                setCallback {post ->
                    val bundle = Bundle()
                    bundle.putString("PostId",post.id)
                    findNavController().navigate(
                        R.id.singlePostFragment,bundle
                    )
                }
            }
        }
        binding.recyclerView.adapter = postPagingAdapter
/*
        binding.bottomNavigation.setOnClickListener {
            findNavController().navigate(
                FeedFragmentDirections.actionFeedFragmentToProfileFragment()
            )
        }

 */

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                FeedFragmentDirections.actionFeedToPostFragment()
            )
        }


        super.onViewCreated(view, savedInstanceState)
        /*
        val navGraph = findNavController().navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.profileFragment)
        findNavController().graph = navGraph
         */
    }
}