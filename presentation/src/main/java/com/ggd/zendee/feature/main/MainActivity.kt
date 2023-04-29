package com.ggd.zendee.feature.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.window.SplashScreen
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityMainBinding
import java.util.logging.Handler

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    override fun preLoad() {
        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepVisibleCondition() { keepSplashOnScreen }
        android.os.Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
    }

    @SuppressLint("NewApi")
    override fun start() {
        //TODO("Not yet implemented")
    }
}