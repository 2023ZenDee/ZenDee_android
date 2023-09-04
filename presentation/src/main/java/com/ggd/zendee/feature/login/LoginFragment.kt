package com.ggd.zendee.feature.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ggd.model.login.requests.LoginDto
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels()

    override fun start() {
        binding.btnLogin.setOnClickListener {
            val etId = binding.etId.text.toString()
            val etPwd = binding.etPwd.text.toString()
            viewModel.login(LoginDto(etId, etPwd))
        }
    }

}