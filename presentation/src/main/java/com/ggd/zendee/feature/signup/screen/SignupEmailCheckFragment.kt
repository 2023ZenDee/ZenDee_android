package com.ggd.zendee.feature.signup.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.email.EmailCheckRequestModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentSignupEmailCheckBinding
import com.ggd.zendee.feature.signup.viewmodel.SignupViewModel

class SignupEmailCheckFragment: BaseFragment<FragmentSignupEmailCheckBinding, SignupViewModel>(R.layout.fragment_signup_email_check) {

    override val viewModel: SignupViewModel by activityViewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnStartZendee.setOnClickListener {
            val etEmail = binding.etEmailCheck.text.toString()

            if (etEmail.isNotEmpty()) {
                with(viewModel) {
                    checkEmail(EmailCheckRequestModel(viewModel.email.value.toString())) // 인증 확인

                    when(emailIsChecked.value) {
                        true -> {
                            register(
                                RegisterRequestModel(
                                    email.value.toString(),
                                    userId.value.toString(),
                                    password.value.toString(),
                                    nick.value.toString()
                                )
                            )
                            findNavController().navigate(R.id.action_signupEmailCheckFragment_to_mapFragment)
                        }
                        else -> {
                            Toast.makeText(context, "인증코드를 다시 확인해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "인증코드를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}