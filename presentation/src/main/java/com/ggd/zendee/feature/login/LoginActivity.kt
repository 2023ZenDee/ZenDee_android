package com.ggd.zendee.feature.login

import android.annotation.SuppressLint
import android.text.InputType
import androidx.activity.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override val viewModel: LoginViewModel by viewModels()

    override fun preLoad() {}

    @SuppressLint("ClickableViewAccessibility")
    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.etId.requestFocus() // linearLayout 안에 editText를 넣어도 작동된다!!

    }
}