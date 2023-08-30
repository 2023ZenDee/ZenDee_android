package com.ggd.zendee.feature.signup

import android.util.Log
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupNicknameBinding


//fun NavController.safeNavigate(direction: NavDirections) {
//    currentDestination?.getAction(direction.actionId)?.run {
//        navigate(direction)
//    }
//}

class SignupNicknameFragment : BaseFragment<FragmentSignupNicknameBinding, SignupViewModel>(R.layout.fragment_signup_nickname) {

    override val viewModel: SignupViewModel by viewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
//            SignupActivity().finish()
        }

        binding.btnNext.setOnClickListener { view ->
//            changeFragment(SignupIdFragment())
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_signupNicknameFragment_to_signupIdFragment)

            //view.findNavController().navigate(R.id.action_signupNicknameFragment_to_signupIdFragment)
//            findNavController().safeNavigate(SignupNicknameFragmentDirections.actionSignupNicknameFragmentToSignupIdFragment())

//            if (binding.etNickname.isEmpty()) {
//                binding.tvInsertCheck.visibility = View.VISIBLE
//            } else {
//                SignupActivity().navigateAnotherFragment(R.id.action_signupNicknameFragment_to_signupIdFragment)
//            }
        }

    }

}