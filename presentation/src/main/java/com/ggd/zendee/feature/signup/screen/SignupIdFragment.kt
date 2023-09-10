package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupIdBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupIdFragment : BaseFragment<FragmentSignupIdBinding, SignupViewModel>(R.layout.fragment_signup_id) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.btnNext.setOnClickListener {
            val etId = binding.etId.text.toString()
            if (etId.isNotEmpty()) {
                viewModel.setUserId(etId)
                findNavController().navigate(R.id.action_signupIdFragment_to_signupPwdFragment)
            } else {
                Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}