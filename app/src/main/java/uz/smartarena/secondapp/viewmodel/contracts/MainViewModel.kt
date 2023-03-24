package uz.smartarena.secondapp.viewmodel.contracts

import android.net.Uri
import androidx.lifecycle.LiveData

interface MainViewModel {
    val uploadLiveData: LiveData<Boolean>
    val getImageFromLocalLiveData: LiveData<Unit>
    val loadImageLiveData:LiveData<Uri>
    val messageLiveData:LiveData<String>
    fun setUri(uri: Uri)
    fun getImageFromGallery()
    fun uploadImage()
}