package com.example.homework2.presentation.profile

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        val builder = MaterialAlertDialogBuilder(requireContext())

        builder.setMessage("Вы уверены, что хотите выйти?")
        builder.setCancelable(true)
        builder.setTitle("Выход")
        builder.setPositiveButton("Принять", DialogInterface.OnClickListener() { dialog, which ->
            findNavController().graph.setStartDestination(R.id.authFragment)

        })
        builder.setNegativeButton("Отмена", DialogInterface.OnClickListener() { dialog, which ->
        })
        viewModel.getProfile("huntams")//prefs.username.toString())

        viewModel.profileLiveData.observe(viewLifecycleOwner) { viewProfile ->
            builder.setTitle(viewProfile.subscribed.toString())
            profAdapter.apply {
                submitList(mutableListOf(viewProfile))
                setCallback { profile ->
                    if (profile.username == prefs.username.toString()) {

                        findNavController().navigate(
                            ProfileFragmentDirections.actionProfileToProfileEditFragment()
                        )
                    } else {
                        if (!viewProfile.subscribed)
                            viewModel.subscribe(username = viewProfile.username)
                        else
                            viewModel.unsubscribe(viewProfile.username)
                    }
                    viewModel.getProfile("huntams")

                }
                viewModel.usernameLiveData.observe(viewLifecycleOwner) {
                    //builder.setTitle(viewProfile.toString())
                    builder.setTitle(viewProfile.subscribed.toString())
                    viewModel.getProfile("huntams")
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
        viewModel.getProfilePosts("evo")//prefs.username.toString())

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

        /*
        binding.toolbar.setOnMenuItemClickListener {
            builder.create().show()
            true
        }

         */
        binding.recyclerView.adapter = ConcatAdapter(profAdapter, imageAdapter, postPagingAdapter)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileToPostFragment()
            )

            //AuthFragmentDirections.actionAuthFragmentToProfileFragment()
        }
        super.onViewCreated(view, savedInstanceState)
        /*
        val navGraph = findNavController().navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.profileFragment)
        findNavController().graph = navGraph
         */
    }
}