package com.androiddevs.gdmvvmnewsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp: Application() {

    companion object {
    lateinit var appInstance : MainApp
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}