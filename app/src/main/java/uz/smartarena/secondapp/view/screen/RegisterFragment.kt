package uz.smartarena.secondapp.view.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import uz.smartarena.secondapp.databinding.FragmentRegisterBinding
import uz.smartarena.secondapp.viewmodel.RegisterViewModelImpl
import uz.smartarena.secondapp.viewmodel.contracts.RegisterViewModel

class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
        binding.apply {
            register.setOnClickListener {
                viewModel.createUser(email.text.toString(), password.text.toString())
            }
        }
        return binding.root
    }

    private val successObserver = Observer<Unit> {
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
}