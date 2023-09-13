package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
            val email = binding.etEmail.text.toString()

            if (email.isNotEmpty()) {
                viewModel.setEmail(email)
                findNavController().navigate(R.id.signupEmailFragment_to_signupEmailCheckFragment)
            } else {
                Toast.makeText(context, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}