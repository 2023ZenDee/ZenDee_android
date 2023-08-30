package com.ggd.zendee.feature.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.feature.login.LoginActivity
import com.ggd.zendee.feature.signup.SignupActivity

class StartActivity : AppCompatActivity() {

    private val btnRegister: Button by lazy {
        findViewById(R.id.btn_register)
    }

    private val btnGoogle: Button by lazy {
        findViewById(R.id.btn_google)
    }

    private val tvLogin: TextView by lazy {
        findViewById(R.id.tv_login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnRegister.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
        }

        btnGoogle.setOnClickListener {
            //TODO
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

    }
}