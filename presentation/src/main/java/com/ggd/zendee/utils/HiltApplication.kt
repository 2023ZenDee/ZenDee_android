package com.ggd.zendee.utils

import android.app.Application
import com.ggd.utils.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class HiltApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceManager
    }

    override fun onCreate() {
        prefs = PreferenceManager(applicationContext)
        super.onCreate()
    }
}