package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupIdBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupIdFragment: BaseFragment<FragmentSignupIdBinding, SignupViewModel>(R.layout.fragment_signup_id) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
            val userId = binding.etId.text.toString()
            if (userId.isNotEmpty()) {
                viewModel.setUserId(userId)
                findNavController().navigate(R.id.signupIdFragment_to_signupPasswordFragment)
            } else {
                Toast.makeText(context, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}