package com.example.homework2.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.remote.model.RegistrationRequest
import com.example.homework2.databinding.AuthorizationFragmentBinding
import com.example.homework2.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.authorization_fragment) {

    private val prefs = context?.let { PrefsStorage(it) }
    private val viewModel by viewModels<ProfileViewModel>()
    private val binding by viewBinding(AuthorizationFragmentBinding::bind)
    private var free = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            continueButton.setOnClickListener {
                if (!free) {
                    viewModel.checkUsername(usernameTextInputEditText.text.toString())
                    viewModel.usernameLiveData.observe(viewLifecycleOwner) {
                        if (it == "Free") {
                            free = true
                            headTextView.text = it
                            usernameTextInputLayout.isEnabled = false
                            passwordTextInputLayout.visibility = View.VISIBLE
                        }
                        /*
                    if(binding.usernameTextInputEditText.length>3)
                        binding.headTextView.text = "rabotaet"

                     */

                    }
                } else {
                    if (passwordTextInputEditText.length() > 3) {
                        if (prefs?.token?.isNotEmpty() == true)
                            prefs.token = ""
                        viewModel.getToken(
                            registrationRequest = RegistrationRequest(
                                username = usernameTextInputEditText.text.toString(),
                                password = passwordTextInputEditText.text.toString(),
                            )
                        )
                        viewModel.usernameLiveData.observe(viewLifecycleOwner) {
                            prefs?.token = it
                            headTextView.text = it
                        }

                        /*
                        findNavController().navigate(
                            AuthFragmentDirections.actionAuthFragmentToProfileFragment()
                        )

                         */
                    }
                    headTextView.text = passwordTextInputEditText.text
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}