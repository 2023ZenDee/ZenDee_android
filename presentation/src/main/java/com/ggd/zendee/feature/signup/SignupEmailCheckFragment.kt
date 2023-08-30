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
import com.ggd.zendee.databinding.FragmentSignupEmailCheckBinding
import com.ggd.zendee.databinding.FragmentSignupIdBinding
import com.ggd.zendee.databinding.FragmentSignupNicknameBinding

class SignupEmailCheckFragment : BaseFragment<FragmentSignupEmailCheckBinding, SignupViewModel>(R.layout.fragment_signup_email_check) {

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
            if (binding.etNickname.isEmpty()) {
                binding.tvInsertCheck.visibility = View.VISIBLE
            }
        }

    }

//    override fun onBackPressed() {
//        val navHost = supportFragmentManager.findFragmentById(R.id.nav_login_fragment)
//        navHost?.let { navFragment ->
//            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
//                if (fragment is LandingFragment) {
//                    finish()
//                } else {
//                    super.onBackPressed()
//                }
//            }
//        }
//    }

}