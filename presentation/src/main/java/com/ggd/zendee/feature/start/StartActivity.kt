package com.ggd.zendee.feature.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityMainBinding
import com.ggd.zendee.databinding.ActivityStartBinding
import com.ggd.zendee.feature.login.LoginActivity

class StartActivity : AppCompatActivity() {

    private val btn_register: Button by lazy {
        findViewById(R.id.btn_register)
    }

    private val btn_google: Button by lazy {
        findViewById(R.id.btn_google)
    }

    private val tv_login: TextView by lazy {
        findViewById(R.id.tv_login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_register.setOnClickListener {
            //TODO
        }

        btn_google.setOnClickListener {
            //TODO
        }

        tv_login.setOnClickListener {
            startActivity(LoginActivity::class.java)
        }

    }

    fun AppCompatActivity.startActivity(activity: Class<*>) {
        startActivity(Intent(applicationContext, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
    }
}