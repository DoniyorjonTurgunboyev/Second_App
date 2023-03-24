package uz.smartarena.secondapp.app

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
        const val accountType = "uz.smartArena"
    }
}