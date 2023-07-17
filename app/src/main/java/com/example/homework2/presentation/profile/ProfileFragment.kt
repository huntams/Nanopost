package com.example.homework2.presentation.profile

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.DataImages
import com.example.homework2.data.DataProfile
import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.model.Profile
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.databinding.FragmentProfileBinding
import com.example.homework2.presentation.auth.AuthFragmentDirections
import com.example.homework2.presentation.imagesCard.DataImagesCard
import com.example.homework2.presentation.imagesCard.ImagesCardAdapter
import com.example.homework2.presentation.postViewCard.PostAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.serialization.builtins.serializer
import java.lang.annotation.Inherited
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)


    @Inject
    lateinit var prefs: PrefsStorage

    @Inject
    lateinit var profAdapter: ProfileAdapter


    @Inject
    lateinit var postPagingAdapter: PostPagingAdapter


    @Inject
    lateinit var imageAdapter: ImagesCardAdapter

    private val viewModel by viewModels<ProfileViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getProfile(prefs.profile.toString())//prefs.username.toString())

        viewModel.profileLiveData.observe(viewLifecycleOwner) { viewProfile ->
            profAdapter.apply {
                submitList(mutableListOf(viewProfile))
                setCallback { profile ->
                    if (profile.username == prefs.username) {
                        findNavController().navigate(
                            ProfileFragmentDirections.actionProfileToProfileEditFragment()
                        )
                    } else {
                        if (!profile.subscribed)
                            viewModel.subscribe(username = profile.username)
                        else
                            viewModel.unsubscribe(profile.username)
                    }
                    viewModel.getProfile(prefs.profile.toString())

                }

            }
            imageAdapter.apply {
                setCallback {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileToImageFragment()
                    )
                }
                submitList(mutableListOf(viewProfile))
            }
        }
        viewModel.usernameLiveData.observe(viewLifecycleOwner) {
            viewModel.getProfile(prefs.profile.toString())
        }
        viewModel.getProfilePosts(prefs.profile.toString())//prefs.username.toString())

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
        binding.recyclerView.adapter = ConcatAdapter(profAdapter, imageAdapter, postPagingAdapter)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileToPostFragment()
            )
        }
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.navigation_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.actionExit) {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setMessage("Вы уверены, что хотите выйти?")
            builder.setCancelable(true)
            builder.setTitle("Выход")
            builder.setPositiveButton(
                "Принять",
                DialogInterface.OnClickListener() { dialog, which ->
                    findNavController().navigate(R.id.authFragment)
                })
            builder.setNegativeButton("Отмена", DialogInterface.OnClickListener() { dialog, which ->
            })
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}