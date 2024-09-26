package com.tollcafe.glidepractice

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class MyApplication : Application() {

    companion object {
        lateinit var sharedViewModel: SharedViewModel
    }

    override fun onCreate() {
        super.onCreate()
        sharedViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
            .create(SharedViewModel::class.java)
    }
}