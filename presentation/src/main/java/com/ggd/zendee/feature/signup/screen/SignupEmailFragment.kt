package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupEmailBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupEmailFragment: BaseFragment<FragmentSignupEmailBinding, SignupViewModel>(R.layout.fragment_signup_email) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnEmailCheck.setOnClickListener {
            val etEmail = binding.etEmail.text.toString()

            if (etEmail.isNotEmpty()) {
                viewModel.setEmail(etEmail)

                with(viewModel) {
                    getEmailCode(EmailRequestModel(email.value.toString())) // 인증코드 발급
                }

                findNavController().navigate(R.id.signupEmailFragment_to_signupEmailCheckFragment)
            } else {
                Toast.makeText(context, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}