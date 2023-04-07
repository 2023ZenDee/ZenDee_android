package com.ggd.zendee.feature.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ggd.zendee.R
import com.ggd.zendee.feature.signup.SignupFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.isLoading.value
            }
        }
        setContentView(R.layout.activity_main)

        //TODO : 만약 처음 젠디를 실행하는 거라면 SignupScreen으로, 아니면 바로 MapScreen으로

        val num: Int = 1

        if (num == 1) {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment, SignupFragment())
            fragmentTransaction.commit()
        } else {

        }



    }
}