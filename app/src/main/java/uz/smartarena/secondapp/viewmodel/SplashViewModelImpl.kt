package uz.smartarena.secondapp.viewmodel

import android.accounts.AccountManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.smartarena.secondapp.authentication.AccountHelper
import uz.smartarena.secondapp.app.App
import uz.smartarena.secondapp.viewmodel.contracts.SplashViewModel

class SplashViewModelImpl : ViewModel(), SplashViewModel {
    private val accountManager: AccountManager = AccountManager.get(App.instance)
    private val accountHelper = AccountHelper.getInstance(App.instance)
    private val auth = Firebase.auth

    init {
        viewModelScope.launch {
            delay(2000)
            val accounts = accountManager.getAccountsByType(App.accountType).toList()
            Log.d("TTT", ": $accounts")
            if (accounts.isNotEmpty()) {
                accounts[0].name
                auth.signInWithEmailAndPassword(accounts[0].name, accountHelper.getPassword(accounts[0])).addOnCompleteListener {
                    if (it.isSuccessful) {
                        openMainScreenLiveData.value = Unit
                        messageLiveData.value = accounts[0].name + " accountiga xush kelibsiz!"
                    }
                }
            } else {
                openLoginScreenLiveData.value = Unit
            }
        }
    }

    override val openLoginScreenLiveData = MutableLiveData<Unit>()
    override val openMainScreenLiveData = MutableLiveData<Unit>()
    override val messageLiveData = MutableLiveData<String>()
}