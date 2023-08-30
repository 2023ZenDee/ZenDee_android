package com.ggd.zendee.feature.main

import android.content.Intent
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
import com.ggd.zendee.feature.start.StartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

//        checkUpdate()
//
//        if (!SharedPreferenceManager.getIsLogin(this)) {
//            startActivityWithFinishAll(StartActivity::class.java)
//            super.onCreate(savedInstanceState)
//        } else {
//            super.onCreate(savedInstanceState)
//            startForMainActivity()
//        }


        var keepSplashOnScreen = true
        val delay = 1000L // 아무리 늘려도 길어지지 않음..

        installSplashScreen().setKeepVisibleCondition() { keepSplashOnScreen }
        android.os.Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        startActivityWithFinishAll(StartActivity::class.java)
        super.onCreate(savedInstanceState)
    }

    fun AppCompatActivity.startActivityWithFinishAll(activity: Class<*>) {
        val intent = Intent(applicationContext, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        this.finishAffinity()
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