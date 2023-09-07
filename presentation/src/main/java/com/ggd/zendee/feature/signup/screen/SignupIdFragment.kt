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
import com.ggd.zendee.databinding.FragmentSignupIdBinding
import com.ggd.zendee.feature.signup.SignupViewModel

class SignupIdFragment : BaseFragment<FragmentSignupIdBinding, SignupViewModel>(R.layout.fragment_signup_id) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.btnNext.setOnClickListener {
            val etId = binding.etId.editText.toString()
            if (etId.isNotEmpty()) {
                viewModel.userId = etId
                findNavController().navigate(R.id.action_signupIdFragment_to_signupPwdFragment)
            } else {
                Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}