package com.ggd.zendee.feature.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupIdBinding
import com.ggd.zendee.databinding.FragmentSignupNickBinding

class SignupIdFragment: BaseFragment<FragmentSignupIdBinding, SignupViewModel>(R.layout.fragment_signup_id) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {

    }

}