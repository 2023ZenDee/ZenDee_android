package com.ggd.zendee.feature.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.view.marginBottom
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityLoginBinding
import com.ggd.zendee.utils.KeyboardEventListener


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override val viewModel: LoginViewModel by viewModels()

    override fun preLoad() {}

    @SuppressLint("ResourceAsColor")
    override fun start() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

//        binding.etId.isFocusableInTouchMode = true
//        binding.etPwd.isFocusableInTouchMode = true

        binding.etId.requestFocus() // linearLayout 안에 editText를 넣어도 작동된다!

        // Observer
        val etId = binding.etId
        val etPwd = binding.etPwd

        etId.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.d("EditText", "isFocused Checked")
                binding.tvEtIdTitle.setTextColor(Color.parseColor("#FFA9ECE3"))
            } else {
                Log.d("EditText", "isNotFocused Checked")
                binding.tvEtIdTitle.setTextColor(Color.parseColor("#FFA5A5A5"))
            }
        }

        etId.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.d("EditText", "isFocused Checked")
                binding.tvEtPwdTitle.setTextColor(Color.parseColor("#FFA9ECE3"))
            } else {
                Log.d("EditText", "isNotFocused Checked")
                binding.tvEtPwdTitle.setTextColor(Color.parseColor("#FFA5A5A5"))
            }
        }


//        etId.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                Log.d("EditText", "isFocused Checked")
//                binding.tvEtIdTitle.setTextColor(Color.parseColor("#FFA9ECE3"))
//            } else {
//                Log.d("EditText", "isNotFocused Checked")
//                binding.tvEtIdTitle.setTextColor(Color.parseColor("#FFA5A5A5"))
//            }
//        }

//        etPwd.setOnFocusChangeListener { v, hasFocus ->
//            if(hasFocus) {
//                Log.d("EditText", "isFocused Checked")
//                binding.tvEtPwdTitle.setTextColor(Color.parseColor("#FFA9ECE3"))
//            } else {
//                Log.d("EditText", "isNotFocused Checked")
//                binding.tvEtPwdTitle.setTextColor(Color.parseColor("#FFA5A5A5"))
//            }
//        }

//        etId.setOnClickListener { etIdIsFocused = true }


        // tvCount가 바뀔 때마다 tvCount.text를 tvCount로 변환시킴
//        etIdIsFocused.observe(this) {  isFocused ->
//            if (isFocused) {
//                Log.d("EditText", "isFocused Checked")
//                binding.tvEtIdTitle.setTextColor(Color.parseColor("#FFA9ECE3"))
//            } else if (!isFocused) {
//                Log.d("EditText", "isNotFocused Checked")
//                binding.tvEtIdTitle.setTextColor(Color.parseColor("#FFA5A5A5"))
//            }
//        }
//
//        etPwdIsFocused.observe(this) {  isFocused ->
//            if(isFocused) {
//                Log.d("EditText", "isFocused Checked")
//                binding.tvEtPwdTitle.setTextColor(Color.parseColor("#FFA9ECE3"))
//            } else if (!isFocused) {
//                Log.d("EditText", "isNotFocused Checked")
//                binding.tvEtPwdTitle.setTextColor(Color.parseColor("#FFA5A5A5"))
//            }
//        }
//


    }

//    override fun onResume() {
//        super.onResume()
//        KeyboardEventListener(this) { isOpen ->
//            if (isOpen) {
//                binding.btnLogin.setBackgroundResource(R.drawable.on_click_edittext_button_container_shadow)
//            }
//        }
//    }
}