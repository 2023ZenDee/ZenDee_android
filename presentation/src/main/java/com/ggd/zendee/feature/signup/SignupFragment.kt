package com.ggd.zendee.feature.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding, BaseViewModel>(R.layout.fragment_signup) {

    override val viewModel: BaseViewModel by viewModels()

    override fun start() {

        binding.btnStart.setOnClickListener {

        }
    }
}