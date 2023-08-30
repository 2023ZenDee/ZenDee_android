package com.ggd.utils

import android.app.Application
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