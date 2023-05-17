package com.example.homework2.presentation.postViewCard

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import coil.load
import com.example.homework2.R
import com.example.homework2.data.DataProfile
import com.example.homework2.databinding.ActivityPostBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.homework2.databinding.FragmentAddPostBinding
import com.example.homework2.presentation.service.CreatePostService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_add_post) {

    @Inject
    lateinit var postAdapter: AddPostAdapter
    //private val viewModel by viewModels<CreatePostService>()

    private var uri: ByteArray = byteArrayOf()
    private val binding by viewBinding(FragmentAddPostBinding::bind)
    private val listUri: MutableList<Uri> = mutableListOf()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriImage ->
            if (uriImage != null) {

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


        /*
        val data = intent.extras?.getParcelable<DataProfile>("ARG_TEXT_KEY")
        with(binding) {
            textViewName.text = data?.name
            textViewDate.text = data?.date
            imageViewPost.load(data?.link)
            textViewPost.text = data?.title
        }
         */



        with(binding) {
            buttonAddImage.setOnClickListener {

                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                Toast.makeText(context, pickMedia.toString(), Toast.LENGTH_SHORT).show()
                recyclerView.adapter = postAdapter.apply {
                    setCallback {
                        //listUri.remove(it.uri)
                    }
                }
            }
            findNavController().graph.setStartDestination(R.id.postFragment)
            findNavController().clearBackStack(R.id.authFragment)
            toolbar.setNavigationOnClickListener {


                /*
                Toast.makeText(
                    context,
                    "сервис запущен",
                    Toast.LENGTH_SHORT
                ).show()

                 */
                findNavController().popBackStack()
            }
            toolbar.setOnMenuItemClickListener {
                activity?.startService(
                    CreatePostService.newIntent(
                        requireContext(),
                        editTextNote.text.toString(),
                        listUri
                    )
                )
                findNavController().popBackStack()
            }
            recyclerView.setOnClickListener {
                if (uri.isNotEmpty()) {
                    findNavController().popBackStack()
                } else
                    Toast.makeText(
                        context,
                        "Изображение не было выбрано",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

    }

}