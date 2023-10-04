package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupPasswordBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupPasswordFragment: BaseFragment<FragmentSignupPasswordBinding, SignupViewModel>(R.layout.fragment_signup_password) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
            val password = binding.etPwd.text.toString()
            val passwordCheck = binding.etPwdCheck.text.toString()
            if (password.isNotEmpty() && passwordCheck.isNotEmpty()) {
                viewModel.setPassword(password)
                findNavController().navigate(R.id.signupPasswordFragment_to_signupEmailFragment)
            } else {
                Toast.makeText(context, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}