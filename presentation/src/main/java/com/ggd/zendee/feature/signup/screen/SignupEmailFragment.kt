package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupEmailBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupEmailFragment : BaseFragment<FragmentSignupEmailBinding, SignupViewModel>(R.layout.fragment_signup_email) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.btnEmailCheck.setOnClickListener {
            val etEmail = binding.etEmail.tag.toString()

            if (etEmail.isNotEmpty()) {
                viewModel.setEmail(etEmail)
                //sendEmailCode(etEmail)
                findNavController().navigate(R.id.action_signupEmailFragment_to_signupCheckFragment)
            } else {
                Toast.makeText(context, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmailCode(email : String) {
        // todo : 이메일에 인증코드 보내는 로직 필요
    }


}