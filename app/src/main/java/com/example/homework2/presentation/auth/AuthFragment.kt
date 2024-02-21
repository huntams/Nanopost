package com.example.homework2.presentation.auth

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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.authorization_fragment) {


    private val viewModel by viewModels<AuthViewModel>()
    private val binding by viewBinding(AuthorizationFragmentBinding::bind)
    private var free = false

    @Inject
    lateinit var prefs: PrefsStorage
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
                                prefs.username = usernameTextInputEditText.text.toString()
                                prefs.profile = usernameTextInputEditText.text.toString()
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
                        findNavController().navigate(
                            AuthFragmentDirections.actionAuthFragmentToFeed()
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