package uz.infinity.app.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.app.R
import uz.infinity.app.data.model.CommandEnum
import uz.infinity.app.data.model.MusicData
import uz.infinity.app.databinding.ScreenPlayListBinding
import uz.infinity.app.presentation.controller.MusicManager
import uz.infinity.app.presentation.ui.adapter.MusicAdapter
import uz.infinity.app.presentation.ui.service.MusicService
import uz.infinity.app.presentation.viewmodel.PlayListViewModel
import uz.infinity.app.presentation.viewmodel.impl.PlayListViewModelImpl
import uz.infinity.app.utils.myApply

@AndroidEntryPoint
class PlayListScreen : Fragment(R.layout.screen_play_list) {
    private val binding by viewBinding(ScreenPlayListBinding::bind)
    private val viewModel: PlayListViewModel by viewModels<PlayListViewModelImpl>()
    private val adapter = MusicAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        musicList.layoutManager = LinearLayoutManager(requireContext())
        musicList.adapter = adapter
        adapter.cursor = MusicManager.cursor

        bottomPart.setOnClickListener {
            findNavController().navigate(R.id.action_playListScreen_to_listenScreen)
        }
        buttonNextScreen.setOnClickListener { startService(CommandEnum.NEXT) }
        buttonPrevScreen.setOnClickListener { startService(CommandEnum.PREV) }
        buttonManageScreen.setOnClickListener { startService(CommandEnum.MANAGE) }
        adapter.selectMusicPositionListener {
            MusicManager.selectMusicPos = it
            startService(CommandEnum.PLAY)
        }

        MusicManager.playMusicLiveData.observe(viewLifecycleOwner, playMusicObserver)
        MusicManager.isPlayingLiveData.observe(viewLifecycleOwner, isPlayingObserver)


        viewModel.cursorRefFlow.onEach {
            it?.let {
                showMusics(it)
            }
        }.launchIn(lifecycleScope)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showMusics(cursor: Cursor) {
        adapter.cursor = cursor
        adapter.notifyDataSetChanged()
    }

    private fun startService(commandEnum: CommandEnum) {
        val intent = Intent(requireContext(), MusicService::class.java)
        MusicManager.lastCommand = commandEnum
        if (Build.VERSION.SDK_INT >= 26) {
            requireContext().startForegroundService(intent)
        } else requireContext().startService(intent)
    }

    private val playMusicObserver = Observer<MusicData> { data ->
        binding.apply {
            textMusicNameScreen.text= data.title
            textArtistNameScreen.text = data.artist
        }
    }
    private val isPlayingObserver = Observer<Boolean> {
        if (it) binding.buttonManageScreen.setImageResource(R.drawable.ic_pause)
        else binding.buttonManageScreen.setImageResource(R.drawable.ic_play)
    }

}

