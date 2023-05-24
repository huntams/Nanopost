package com.example.homework2.presentation.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
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

    lateinit var searchView: SearchView

    @Inject
    lateinit var prefs: PrefsStorage

    @Inject
    lateinit var profileCompactPagingAdapter: ProfileCompactPagingAdapter

    @Inject
    lateinit var postPagingAdapter: FeedPagingAdapter

    private val viewModel by viewModels<FeedViewModel>()
    private var query = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.getFeed()

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            postPagingAdapter.apply {
                submitData(viewLifecycleOwner.lifecycle, it)
                setCallback { post ->
                    val bundle = Bundle()
                    bundle.putString("PostId", post.id)
                    findNavController().navigate(
                        R.id.singlePostFragment, bundle
                    )
                }
            }
        }
        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            profileCompactPagingAdapter.apply {
                setCallback {
                    //prefs.profile = it.username
                    findNavController().navigate(R.id.Profile)
                }
                setCallbackButton {
                    if(prefs.username !=it.username){
                        if (!it.subscribed)
                            viewModel.subscribe(username = it.username)
                        else
                            viewModel.unsubscribe(it.username)
                    }
                    else{
                        findNavController().navigate(R.id.profileEditFragment)
                    }
                }
                submitData(viewLifecycleOwner.lifecycle, it)

            }
        }
        viewModel.usernameLiveData.observe(viewLifecycleOwner){
            viewModel.searchProfiles(query)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        searchView = menu.findItem(R.id.actionSearch).actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    query = p0
                    viewModel.usernameLiveData.observe(viewLifecycleOwner){
                        viewModel.searchProfiles(query)
                        Toast.makeText(requireContext(),"this is work",Toast.LENGTH_LONG).show()
                    }
                    viewModel.searchProfiles(query)
                    binding.recyclerView.adapter = profileCompactPagingAdapter
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    query = p0
                    viewModel.searchProfiles(query)
                    binding.recyclerView.adapter = profileCompactPagingAdapter
                }
                return true

            }
        })



        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.actionSearch) {

            binding.recyclerView.adapter = profileCompactPagingAdapter
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}