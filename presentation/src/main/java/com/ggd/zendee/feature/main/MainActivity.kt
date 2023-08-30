package com.ggd.zendee.feature.main

import androidx.activity.viewModels
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun preLoad() {
        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepVisibleCondition() { keepSplashOnScreen }
        android.os.Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
    }

    override fun start() {
        val actionBar = supportActionBar
        actionBar!!.hide()

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
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
}