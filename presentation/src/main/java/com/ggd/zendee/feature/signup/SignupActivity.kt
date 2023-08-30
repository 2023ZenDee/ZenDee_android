package com.ggd.zendee.feature.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.ggd.zendee.R

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        showSignupNicknameFragment()
    }

    private fun showSignupNicknameFragment() {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, SignupNicknameFragment())
        transaction.commit()
    }
}