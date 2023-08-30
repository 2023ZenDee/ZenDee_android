package com.ggd.zendee.feature.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.ggd.model.login.LoginDto
import com.ggd.model.login.TokenDto
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityLoginBinding
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {


    override val viewModel: LoginViewModel by viewModels()

    override fun start() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.etId.requestFocus()

        binding.btnLogin.setOnClickListener {
            val typedId = binding.etId.text.toString()
            val typedPwd = binding.etPwd.text.toString()

            repeatOnStarted { viewModel.eventFlow.collect { event -> handleEvent(event) } }

            binding.btnLogin.setOnClickListener {
                viewModel.login(LoginDto(typedId, typedPwd))
            }
        }
    }

    private fun handleEvent(event: LoginViewModel.Event): Any =
        when (event) {
            is LoginViewModel.Event.SuccessLogin -> {
                Log.d(TAG, "handleEvent: Login Success!!")
//                viewModel.token(TokenDto(event.code.authCode))
            }
            is LoginViewModel.Event.SuccessToken -> {
                Log.d(TAG, "handleEvent: Token Success!!")
//                startActivity(Intent(LoginActivity(), MainActivity::class.java))
//                finish()
            }
            is LoginViewModel.Event.UnkownException -> shortToast("알 수 없는 오류가 발생했습니다.")
        }

    private fun shortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    companion object {
        const val TAG = "LoginActivity"
    }

}

