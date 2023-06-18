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

class MainActivity() : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main),
    Parcelable {

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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mapFragment, R.id.profileFragment, R.id.rankingFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
    fun handleBottomNavigation(state : Boolean){
        if (state)binding.navView.visibility = View.VISIBLE
        else binding.navView.visibility = View.INVISIBLE
    }
}