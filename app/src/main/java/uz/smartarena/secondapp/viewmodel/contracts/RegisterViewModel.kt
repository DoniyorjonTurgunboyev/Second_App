package uz.smartarena.secondapp.viewmodel.contracts

import androidx.lifecycle.LiveData

interface RegisterViewModel {
    val errorLiveData: LiveData<String>
    val successLiveData: LiveData<Unit>
    fun createUser(email: String, password: String)
}