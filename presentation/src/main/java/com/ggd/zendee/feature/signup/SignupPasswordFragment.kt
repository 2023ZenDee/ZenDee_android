package com.ggd.zendee.feature.signup

import androidx.fragment.app.activityViewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupNickBinding
import com.ggd.zendee.databinding.FragmentSignupPasswordBinding

class SignupPasswordFragment: BaseFragment<FragmentSignupPasswordBinding, SignupViewModel>(R.layout.fragment_signup_password) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {

    }

}