package com.example.kmpxmachinelearning

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kmpxmachinelearning.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@MyApplication)
            }
        )
//        FirebaseApp.initializeApp(this)
    }
}