package com.ggd.zendee.feature.main

<<<<<<< HEAD
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
class MainActivity() : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main),
    Parcelable {
=======
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ggd.zendee.feature.start.StartActivity
>>>>>>> #12_register_screen

    override val viewModel: MainViewModel by viewModels()

    constructor(parcel: Parcel) : this() {
    }

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

<<<<<<< HEAD
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
=======
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
>>>>>>> #12_register_screen

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mapFragment, R.id.profileFragment, R.id.rankingFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}