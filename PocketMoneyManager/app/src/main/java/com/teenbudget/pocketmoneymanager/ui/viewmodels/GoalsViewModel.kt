package com.teenbudget.pocketmoneymanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teenbudget.pocketmoneymanager.data.SavingsGoal
import com.teenbudget.pocketmoneymanager.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    
    val goals: StateFlow<List<SavingsGoal>> = repository.getAllSavingsGoals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun addMoneyToGoal(goalId: Long, amount: Double) {
        viewModelScope.launch {
            repository.addMoneyToGoal(goalId, amount)
        }
    }
}