package uz.smartarena.secondapp.view.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import uz.smartarena.secondapp.viewmodel.LoginViewModelImpl
import uz.smartarena.secondapp.viewmodel.contracts.LoginViewModel
import uz.smartarena.secondapp.R
import uz.smartarena.secondapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.apply {
            login.setOnClickListener {
                viewModel.login(email.text.toString(), password.text.toString())
            }
            register.setOnClickListener {
                viewModel.openRegister()
            }

        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.openRegisterLiveData.observe(this, openRegisterObserver)
        viewModel.openMainScreenLiveData.observe(this,openMainScreenObserver)
    }

    private val successObserver = Observer<Unit> {
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
    }

    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val openRegisterObserver = Observer<Unit> {
        parentFragmentManager.commit {
            replace<RegisterFragment>(R.id.container).addToBackStack("register")
        }
    }
    private val openMainScreenObserver = Observer<Unit> {
        parentFragmentManager.commit {
            replace<MainFragment>(R.id.container)
        }
    }
}