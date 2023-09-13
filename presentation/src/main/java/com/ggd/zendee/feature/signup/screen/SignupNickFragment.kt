package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupNickBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupNickFragment: BaseFragment<FragmentSignupNickBinding, SignupViewModel>(R.layout.fragment_signup_nick) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
            val nick = binding.etNick.text.toString()

            if (nick.isNotEmpty()) {
                viewModel.setNick(nick)
                findNavController().navigate(R.id.signupNickFragment_to_signupIdFragment)
            } else {
                Toast.makeText(context, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}