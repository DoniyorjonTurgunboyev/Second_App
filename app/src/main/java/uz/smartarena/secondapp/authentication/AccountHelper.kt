package uz.smartarena.secondapp.authentication

import android.accounts.*
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle

class AccountHelper private constructor(context: Context) {
    private var accountManager: AccountManager? = null
    private var context: Context? = null
    fun addAccountExplicitly(account: Account?, password: String?, userdata: Bundle?): Boolean {
        return accountManager!!.addAccountExplicitly(account, password, userdata)
    }

    fun getPassword(account: Account): String {
        return accountManager!!.getPassword(account)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: AccountHelper? = null

        fun getInstance(context: Context): AccountHelper {
            if (instance == null) {
                instance = AccountHelper(context)
            }
            return instance!!
        }
    }

    init {
        accountManager = AccountManager.get(context)
        this.context = context
    }
}