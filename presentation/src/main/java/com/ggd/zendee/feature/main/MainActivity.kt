package com.ggd.zendee.feature.main

import android.app.Activity
import android.os.Build
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
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
class   MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun preLoad() {
        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        android.os.Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
    }

    override fun start() {

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mapFragment, R.id.rankingFragment, R.id.profileFragment
            )
        )

        navView.setupWithNavController(navController)


//        checkUpdate()
//
//        if (!SharedPreferenceManager.getIsLogin(this)) {
//            startActivityWithFinishAll(StartActivity::class.java)
//            super.onCreate(savedInstanceState)
//        } else {
//            super.onCreate(savedInstanceState)
//            startForMainActivity()
//        }
//        setStatusBarTransparent()
    }
    fun handleBottomNavigation(state : Boolean){
        if (state)binding.navView.visibility = View.VISIBLE
        else binding.navView.visibility = View.GONE
    }

//    private fun Activity.setStatusBarTransparent() {
//        window.apply {
//            setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
//        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
//            WindowCompat.setDecorFitsSystemWindows(window, false)
//        }
//    }

}
