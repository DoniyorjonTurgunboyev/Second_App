package uz.smartarena.secondapp.authentication

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticationService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        val accountAuthenticator = AccountAuthenticator(this)
        return accountAuthenticator.iBinder
    }
}