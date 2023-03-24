package uz.smartarena.secondapp.view.screen

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import uz.smartarena.secondapp.databinding.FragmentMainBinding
import uz.smartarena.secondapp.viewmodel.MainViewModelImpl
import uz.smartarena.secondapp.viewmodel.contracts.MainViewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        alertDialog = AlertDialog.Builder(requireContext()).setTitle("Rasm yuklanmoqda").setMessage("Iltimos kuting ...").setCancelable(false)
            .create()
        viewModel.loadImageLiveData.observe(this, loadImageObserver)
        viewModel.getImageFromLocalLiveData.observe(this, loadGalleryObserver)
        viewModel.uploadLiveData.observe(this, dialogObserver)
        viewModel.messageLiveData.observe(this) { message -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show() }
        binding.apply {
            select.setOnClickListener {
                viewModel.getImageFromGallery()
            }
            upload.setOnClickListener {
                viewModel.uploadImage()
            }
        }
    }

    private val gallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.image.setImageURI(it)
        it?.let { it1 -> viewModel.setUri(it1) }
    }
    private lateinit var alertDialog: AlertDialog

    private val loadGalleryObserver = Observer<Unit> {
        gallery.launch("image/*")
    }
    private val loadImageObserver = Observer<Uri> {
        Glide.with(requireContext()).load(it).into(binding.image)
    }
    private val dialogObserver = Observer<Boolean> {
        if (it) {
            alertDialog.show()
        } else {
            alertDialog.dismiss()
        }
    }
}