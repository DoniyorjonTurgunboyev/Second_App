package uz.smartarena.secondapp.viewmodel

import android.accounts.Account
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uz.smartarena.secondapp.authentication.AccountHelper
import uz.smartarena.secondapp.app.App
import uz.smartarena.secondapp.viewmodel.contracts.LoginViewModel

class LoginViewModelImpl : ViewModel(), LoginViewModel {
    private val auth = Firebase.auth
    private val accountHelper = AccountHelper.getInstance(App.instance)
    override val errorLiveData = MutableLiveData<String>()
    override val successLiveData = MutableLiveData<Unit>()
    override val openRegisterLiveData = MutableLiveData<Unit>()
    override val openMainScreenLiveData = MutableLiveData<Unit>()

    override fun login(email: String, password: String) {
        if (email.isEmpty()) {
            errorLiveData.value = "Emailni kiriting"
        } else if (password.isEmpty()) {
            errorLiveData.value = "Parolni kiriting"
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    successLiveData.value = Unit
                    val account = Account(email, App.accountType)
                    accountHelper.addAccountExplicitly(account, password, null)
                    openMainScreenLiveData.value = Unit
                } else {
                    errorLiveData.value = "So'rov bekor qilindi"
                }
            }.addOnFailureListener {
                errorLiveData.value = it.message
            }
        }
    }

    override fun openRegister() {
        openRegisterLiveData.value = Unit
    }
}