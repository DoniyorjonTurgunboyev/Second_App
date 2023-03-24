package uz.smartarena.secondapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uz.smartarena.secondapp.viewmodel.contracts.RegisterViewModel

class RegisterViewModelImpl : ViewModel(), RegisterViewModel {
    private val emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+"
    private val auth = Firebase.auth
    override val errorLiveData = MutableLiveData<String>()
    override val successLiveData = MutableLiveData<Unit>()

    override fun createUser(email: String, password: String) {
        if (email.isEmpty()) {
            errorLiveData.value = "Emailni kiriting"
        } else if (!email.matches(emailPattern.toRegex())) {
            errorLiveData.value = "Email xato"
        } else if (password.length < 6) {
            errorLiveData.value = "Parol kamida 6 ta belgidan iborat bo'lishi kerak"
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    successLiveData.value = Unit
                } else {
                    errorLiveData.value = "So'rov bekor qilindi"
                }
            }.addOnFailureListener {
                errorLiveData.value = it.message
            }
        }
    }
}