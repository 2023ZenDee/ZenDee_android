package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupCheckBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupCheckFragment : BaseFragment<FragmentSignupCheckBinding, SignupViewModel>(R.layout.fragment_signup_check) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.btnStartZendee.setOnClickListener {
            val etCheck = binding.etCheckNumber.text.toString()

            if (etCheck.isNotEmpty()) {
                if (etCheck == "true") { // todo : 이메일 인증번호 확인 필요
                    findNavController().navigate(R.id.action_signupCheckFragment_to_mapFragment)
                } else {
                    Toast.makeText(context, "인증번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}