package com.ggd.zendee.feature.main

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityMainBinding
import com.ggd.zendee.feature.signup.SignupFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()
    override fun start() {
        //TODO : 만약 처음 젠디를 실행하는 거라면 SignupScreen으로, 아니면 바로 MapScreen으로
//
//        val num: Int = 1
//
//        if (num == 1) {
//            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//            fragmentTransaction.add(R.id.fragment, SignupFragment())
//            fragmentTransaction.commit()
//        } else {
//
//        }
    }
}