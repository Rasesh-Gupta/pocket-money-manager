package com.teenbudget.pocketmoneymanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teenbudget.pocketmoneymanager.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val userName: String = "Teen",
    val totalCoins: Int = 0,
    val currentStreak: Int = 0,
    val unlockedBadges: Int = 0,
    val isDarkMode: Boolean = false,
    val notificationsEnabled: Boolean = true
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    init {
        loadProfile()
    }
    
    private fun loadProfile() {
        viewModelScope.launch {
            repository.getUserProfile().collect { profile ->
                profile?.let {
                    _uiState.value = _uiState.value.copy(
                        userName = it.name,
                        totalCoins = it.totalCoins,
                        currentStreak = it.currentStreak,
                        isDarkMode = it.isDarkMode,
                        notificationsEnabled = it.notificationsEnabled
                    )
                }
            }
        }
    }
    
    fun toggleDarkMode() {
        viewModelScope.launch {
            repository.updateDarkMode(!_uiState.value.isDarkMode)
        }
    }
    
    fun toggleNotifications() {
        viewModelScope.launch {
            repository.updateNotifications(!_uiState.value.notificationsEnabled)
        }
    }
}