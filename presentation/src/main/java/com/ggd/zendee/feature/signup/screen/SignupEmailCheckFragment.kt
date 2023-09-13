package com.ggd.zendee.feature.signup.screen

import androidx.fragment.app.activityViewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupEmailCheckBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupEmailCheckFragment: BaseFragment<FragmentSignupEmailCheckBinding, SignupViewModel>(R.layout.fragment_signup_email_check) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {

    }

}