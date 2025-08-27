package com.teenbudget.pocketmoneymanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teenbudget.pocketmoneymanager.data.SavingsGoal
import com.teenbudget.pocketmoneymanager.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "Teen",
    val balance: Double = 0.0,
    val weeklySpent: Double = 0.0,
    val weeklyBudget: Double = 500.0,
    val totalCoins: Int = 0,
    val currentStreak: Int = 0,
    val unlockedBadges: Int = 0,
    val activeSavingsGoals: List<SavingsGoal> = emptyList(),
    val selectedCategory: String = ""
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            combine(
                repository.getUserProfile(),
                repository.getWeeklyExpenses(),
                repository.getActiveSavingsGoals()
            ) { profile, weeklyExpenses, goals ->
                _uiState.value = _uiState.value.copy(
                    userName = profile?.name ?: "Teen",
                    balance = repository.getCurrentBalance(),
                    weeklySpent = weeklyExpenses,
                    weeklyBudget = profile?.weeklyBudget ?: 500.0,
                    totalCoins = profile?.totalCoins ?: 0,
                    currentStreak = profile?.currentStreak ?: 0,
                    activeSavingsGoals = goals
                )
            }.collect()
        }
    }
    
    fun setSelectedCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }
}