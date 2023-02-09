package uz.infinity.app.presentation.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.infinity.app.R
import uz.infinity.app.databinding.ScreenSplashBinding
import uz.infinity.app.presentation.viewmodel.SplashViewModel
import uz.infinity.app.presentation.viewmodel.impl.SplashViewModelImpl
import uz.infinity.app.utils.checkPermissions

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().checkPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            viewModel.getMusicFromLocal()
        }
    }
}


