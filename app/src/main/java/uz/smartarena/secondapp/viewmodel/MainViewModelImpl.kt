package uz.smartarena.secondapp.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import uz.smartarena.secondapp.viewmodel.contracts.MainViewModel

class MainViewModelImpl : ViewModel(), MainViewModel {
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var uri: Uri
    private val authId = FirebaseAuth.getInstance().currentUser!!.uid
    override val uploadLiveData = MutableLiveData<Boolean>()

    override val getImageFromLocalLiveData = MutableLiveData<Unit>()

    override val loadImageLiveData = MutableLiveData<Uri>()
    override val messageLiveData = MutableLiveData<String>()

    init {
        uploadLiveData.value = true
        try {
        storageRef.child("images").child(authId).downloadUrl.addOnSuccessListener {
            uploadLiveData.value = false
            messageLiveData.value ="Rasm ko'chirilmoqda"
            loadImageLiveData.value = it
        }.addOnFailureListener {
            uploadLiveData.value = false
        }}catch (e:Exception){
            messageLiveData.value = e.message
        }
    }

    override fun setUri(uri: Uri) {
        this.uri = uri
    }

    override fun getImageFromGallery() {
        getImageFromLocalLiveData.value = Unit
    }

    override fun uploadImage() {
        if (this::uri.isInitialized){
            uploadLiveData.value = true
            storageRef.child("images").child(authId).putFile(uri).addOnSuccessListener {
                messageLiveData.value="Rasm muvaffaqiyali yuborildi"
                uploadLiveData.value = false

            }.addOnFailureListener{
                messageLiveData.value = it.message
                uploadLiveData.value = false

            }
        }else{
            messageLiveData.value = "Avval rasm tanlang"
        }
    }
}