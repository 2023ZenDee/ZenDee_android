package com.ggd.zendee.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ggd.zendee.feature.start.StartActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var keepSplashOnScreen = true
        val delay = 1000L // 아무리 늘려도 길어지지 않음..

        installSplashScreen().setKeepVisibleCondition() { keepSplashOnScreen }
        android.os.Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        startActivityWithFinishAll(StartActivity::class.java) // 임시

        super.onCreate(savedInstanceState)

//        checkUpdate()
//
//        if (!SharedPreferenceManager.getIsLogin(this)) {
//            startActivityWithFinishAll(StartActivity::class.java)
//            super.onCreate(savedInstanceState)
//        } else {
//            super.onCreate(savedInstanceState)
//            startForMainActivity()
//        }
    }

    private fun AppCompatActivity.startActivityWithFinishAll(activity: Class<*>) {
        val intent = Intent(applicationContext, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        this.finishAffinity()
    }
}