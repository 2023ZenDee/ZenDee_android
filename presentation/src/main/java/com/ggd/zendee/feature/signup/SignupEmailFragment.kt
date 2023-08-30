package com.ggd.zendee.feature.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupEmailBinding
import com.ggd.zendee.databinding.FragmentSignupIdBinding
import com.ggd.zendee.databinding.FragmentSignupNicknameBinding

class SignupEmailFragment : BaseFragment<FragmentSignupEmailBinding, SignupViewModel>(R.layout.fragment_signup_email) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            changeFragment(SignupPwdFragment())
        }

        binding.btnNext.setOnClickListener {
            changeFragment(SignupEmailCheckFragment())
//            if (binding.etNickname.isEmpty()) {
//                binding.tvInsertCheck.visibility = View.VISIBLE
//            }
        }

    }

}