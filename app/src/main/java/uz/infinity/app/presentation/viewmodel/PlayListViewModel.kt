package uz.infinity.app.presentation.viewmodel

import android.database.Cursor
import kotlinx.coroutines.flow.StateFlow

interface PlayListViewModel {
    val cursorRefFlow: StateFlow<Cursor?>
}

