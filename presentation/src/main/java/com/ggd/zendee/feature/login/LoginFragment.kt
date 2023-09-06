package com.ggd.zendee.feature.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.model.login.requests.LoginDto
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels()


    override fun start() {

        eventHandling()

        binding.btnLogin.setOnClickListener {
            val etId = binding.etId.text.toString()
            val etPwd = binding.etPwd.text.toString()
            viewModel.login(LoginDto(etId, etPwd))
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