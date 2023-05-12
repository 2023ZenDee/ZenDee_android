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
import com.ggd.zendee.databinding.FragmentNicknameBinding

class NicknameFragment : BaseFragment<FragmentNicknameBinding, SignupViewModel>(R.layout.fragment_nickname) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            //TODO : Start Activity 로 넘어가기
        }

//        if (binding.etNickname.isEmpty()) {
//            binding.tvInsertCheck.visibility = View.VISIBLE
//        } else {
//            binding.tvEtNicknameTitle.visibility = View.INVISIBLE
//        }

        binding.btnNext.setOnClickListener {
            //TODO : Fragment 넘어가기
        }
    }

}