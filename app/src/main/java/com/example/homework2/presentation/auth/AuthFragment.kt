package com.example.homework2.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.remote.model.RegistrationRequest
import com.example.homework2.databinding.AuthorizationFragmentBinding
import com.example.homework2.presentation.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.authorization_fragment) {

    private val prefs = context?.let { PrefsStorage(it) }
    private val viewModel by viewModels<AuthViewModel>()
    private val binding by viewBinding(AuthorizationFragmentBinding::bind)
    private var free = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            continueButton.setOnClickListener {
                if (!free) {
                    viewModel.checkUsername(usernameTextInputEditText.text.toString())
                    viewModel.usernameLiveData.observe(viewLifecycleOwner) { answer ->
                        when (answer) {
                            "Free", "Taken" -> {
                                free = true
                                usernameTextInputLayout.isErrorEnabled = false
                                usernameTextInputLayout.isEnabled = false
                                passwordTextInputLayout.visibility = View.VISIBLE
                            }

                            "TooLong" -> usernameTextInputLayout.error =
                                "имя пользователя должен быть меньше  16 символов"

                            "InvalidCharacters" -> usernameTextInputLayout.error =
                                "имя пользователя содержит неподдерживаемые символы"

                            "TooShort" -> usernameTextInputLayout.error =
                                "имя пользователя должен быть больше 3 символов"

                            else -> usernameTextInputEditText.error = "test"
                        }
                        usernameTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)

                    }
                } else {
                    if (passwordTextInputEditText.length() >= 8) {
                        if ("Free" == viewModel.usernameLiveData.value)
                            viewModel.getToken(
                                registrationRequest = RegistrationRequest(
                                    username = usernameTextInputEditText.text.toString(),
                                    password = passwordTextInputEditText.text.toString(),
                                )
                            )
                        else
                            viewModel.getToken(
                                username = usernameTextInputEditText.text.toString(),
                                password = passwordTextInputEditText.text.toString()
                            )
                        viewModel.getToken(
                            username = usernameTextInputEditText.text.toString(),
                            password = passwordTextInputEditText.text.toString()
                        )
                        viewModel.tokenLiveData.observe(viewLifecycleOwner) {
                            Log.i("${prefs?.token}", it)
                            prefs?.token = it
                        }
                        findNavController().graph.setStartDestination(R.id.profileFragment)
                        findNavController().clearBackStack(R.id.authFragment)
                        findNavController().navigate(
                            AuthFragmentDirections.actionAuthFragmentToProfileFragment()
                        )
                    } else
                        passwordTextInputLayout.error =
                            "Пароль должен быть больше 7 символов"
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}