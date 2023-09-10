package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupNicknameBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupNicknameFragment : BaseFragment<FragmentSignupNicknameBinding, SignupViewModel>(R.layout.fragment_signup_nickname) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.btnNext.setOnClickListener {
            val etNick = binding.etNick.text.toString()
            if (etNick.isNotEmpty()) {
                viewModel.setNick(etNick)
                findNavController().navigate(R.id.action_signupNicknameFragment_to_signupIdFragment)
            } else {
                Toast.makeText(context, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}