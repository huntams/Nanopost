package com.example.homework2.presentation.profile

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.databinding.FragmentProfileBinding
import com.example.homework2.presentation.imagesCard.ImagesCardAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val menuHost: MenuHost by lazy { requireActivity() }

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
            profAdapter.also {  }
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
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.navigation_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.actionExit) {
                    val builder = MaterialAlertDialogBuilder(requireContext())
                    builder.setMessage("Вы уверены, что хотите выйти?")
                    builder.setCancelable(true)
                    builder.setTitle("Выход")
                    builder.setPositiveButton(
                        "Принять",
                        DialogInterface.OnClickListener() { _, _ ->
                            findNavController().navigate(R.id.authFragment)
                        })
                    builder.setNegativeButton("Отмена", DialogInterface.OnClickListener() { _, _ ->
                    })
                    builder.create().show()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        super.onViewCreated(view, savedInstanceState)
    }
}