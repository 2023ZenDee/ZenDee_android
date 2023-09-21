package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupEmailBinding
import com.ggd.zendee.feature.signup.screen.state.SignupState
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel
import kotlinx.coroutines.flow.collect

class SignupEmailFragment: BaseFragment<FragmentSignupEmailBinding, SignupViewModel>(R.layout.fragment_signup_email) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnStartZendee.setOnClickListener {
            val etEmail = binding.etEmail.text.toString()

            if (etEmail.isNotEmpty()) {
                with(viewModel) {
                    register(
                        RegisterRequestModel(
                            etEmail,
                            userId.value.toString(),
                            password.value.toString(),
                            nick.value.toString()
                        )
                    )

                    lifecycleScope.launchWhenStarted {
                        signupState.collect { state ->
                            if (state.isSuccess) {
                                findNavController().navigate(R.id.action_signupEmailFragment_to_mapFragment)
                            }
                            if (state.error.isNotEmpty()) {
                                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

//                    getEmailCode(EmailRequestModel(email.value.toString())) // 인증코드 발급?
                }

            } else {
                Toast.makeText(context, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}