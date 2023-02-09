package uz.infinity.app.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.infinity.app.R
import uz.infinity.app.databinding.ScreenPlayBinding
import uz.infinity.app.presentation.viewmodel.PlayViewModel
import uz.infinity.app.presentation.viewmodel.impl.PlayViewModelImpl
import uz.infinity.app.utils.myApply

@AndroidEntryPoint
class PlayScreen : Fragment(R.layout.screen_play) {
    private val binding by viewBinding(ScreenPlayBinding::bind)
    private val viewModel: PlayViewModel by viewModels<PlayViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {


    }
}