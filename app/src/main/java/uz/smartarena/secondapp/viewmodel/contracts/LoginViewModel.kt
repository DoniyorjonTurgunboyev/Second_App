package uz.smartarena.secondapp.viewmodel.contracts

import androidx.lifecycle.LiveData

interface LoginViewModel {
    val errorLiveData: LiveData<String>
    val successLiveData: LiveData<Unit>
    val openRegisterLiveData: LiveData<Unit>
    val openMainScreenLiveData: LiveData<Unit>
    fun login(email: String, password: String)
    fun openRegister()
}