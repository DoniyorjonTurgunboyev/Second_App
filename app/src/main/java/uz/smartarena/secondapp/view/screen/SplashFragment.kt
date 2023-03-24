package uz.smartarena.secondapp.view.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import uz.smartarena.secondapp.R
import uz.smartarena.secondapp.databinding.FragmentSplashBinding
import uz.smartarena.secondapp.viewmodel.SplashViewModelImpl
import uz.smartarena.secondapp.viewmodel.contracts.SplashViewModel

class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        viewModel.openLoginScreenLiveData.observe(viewLifecycleOwner, openLoginScreenObserver)
        viewModel.openMainScreenLiveData.observe(viewLifecycleOwner, openMainScreenObserver)
        viewModel.messageLiveData.observe(viewLifecycleOwner, messageObserver)
        return binding.root
    }

    private val openLoginScreenObserver = Observer<Unit> {
        parentFragmentManager.commit {
            replace<LoginFragment>(R.id.container)
        }
    }
    private val openMainScreenObserver = Observer<Unit> {
        parentFragmentManager.commit {
            replace<MainFragment>(R.id.container)
        }
    }
    private val messageObserver = Observer<String> { message ->
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}