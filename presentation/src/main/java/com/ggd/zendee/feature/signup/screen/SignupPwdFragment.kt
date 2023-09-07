package com.ggd.zendee.feature.signup.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupPwdBinding
import com.ggd.zendee.feature.signup.SignupViewModel

class SignupPwdFragment : BaseFragment<FragmentSignupPwdBinding, SignupViewModel>(R.layout.fragment_signup_pwd) {
    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.btnNext.setOnClickListener {
            val etPwd = binding.etPwd.editText.toString()
            if (etPwd.isNotEmpty()) {
                viewModel.password = etPwd
                findNavController().navigate(R.id.action_signupPwdFragment_to_signupEmailFragment)
            } else {
                Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

}