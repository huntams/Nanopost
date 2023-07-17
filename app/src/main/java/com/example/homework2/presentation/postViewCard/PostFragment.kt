package com.example.homework2.presentation.postViewCard

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.homework2.R
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.databinding.FragmentAddPostBinding
import com.example.homework2.presentation.service.CreatePostService
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_add_post) {

    @Inject
    lateinit var postAdapter: AddPostAdapter

    private val binding by viewBinding(FragmentAddPostBinding::bind)
    private val listUri: MutableList<Uri> = mutableListOf()
    private val addPostData = mutableListOf<AddPostData>()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriImage ->
            if (uriImage != null) {
                addPostData.add(AddPostData(UUID.randomUUID().mostSignificantBits, uriImage))
                listUri.add(uriImage)

                //uri = closeAddImageView.drawToBitmap().convertToByteArray()
                //vision(View.VISIBLE, View.INVISIBLE)
            } else {
                Toast.makeText(context, "Изображение не было выбрано", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    /*
    companion object {
        fun createIntent(context: Context, data: DataProfile) =
            Intent(context, CreatePostService::class.java).apply {
                putExtra("ARG_TEXT_KEY", data)
            }
    }

     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding) {

            recyclerView.adapter = postAdapter.apply {
                submitList(addPostData)
                setCallback {
                    addPostData.remove(it)
                    submitList(addPostData)
                    //listUri.remove(it.uri)
                }
            }
            buttonAddImage.setOnClickListener {

                if (addPostData.size != 4) {

                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    Toast.makeText(context, pickMedia.toString(), Toast.LENGTH_SHORT).show()
                    recyclerView.adapter = postAdapter.apply {
                        submitList(addPostData)
                        setCallback {
                            addPostData.remove(it)
                            submitList(addPostData)
                            //listUri.remove(it.uri)
                        }
                    }
                }
                recyclerView.adapter = postAdapter

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_create_post, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId) {
            findNavController().popBackStack()
        }

        if (item.itemId == R.id.actionAccept) {
            activity?.startService(
                CreatePostService.newIntent(
                    requireContext(),
                    binding.editTextNote.text.toString(),
                    listUri
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

}