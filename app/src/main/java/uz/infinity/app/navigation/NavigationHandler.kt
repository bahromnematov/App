package uz.infinity.memorygamebottcamp4.navigation

import androidx.navigation.NavDirections

interface NavigationHandler {
    suspend fun back()
    suspend fun navigationTo(direction: NavDirections)
}

