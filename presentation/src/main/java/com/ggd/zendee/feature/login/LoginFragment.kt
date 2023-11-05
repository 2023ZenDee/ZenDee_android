package com.ggd.zendee.feature.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.model.auth.LoginRequestModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentLoginBinding
import com.ggd.zendee.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels()


    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        eventHandling()

        binding.btnLogin.setOnClickListener {
            val etId = binding.etId.text.toString()
            val etPwd = binding.etPwd.text.toString()
            viewModel.login(LoginRequestModel(etId, etPwd))
        }

    }

    private fun eventHandling() {
        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when(loginState) {
                LoginViewModel.Event.LoginSuccess -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mapFragment)
                }
                LoginViewModel.Event.LoginFailed -> {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                }
                else -> { }
            }
        }
    }

}