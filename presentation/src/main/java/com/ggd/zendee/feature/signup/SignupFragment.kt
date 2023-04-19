package com.ggd.zendee.feature.signup

import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {

        binding.btnStart.setOnClickListener {

        }
    }
}