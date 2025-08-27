package com.teenbudget.pocketmoneymanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teenbudget.pocketmoneymanager.data.*
import com.teenbudget.pocketmoneymanager.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class AddTransactionUiState(
    val amount: String = "",
    val description: String = "",
    val selectedCategory: String = "",
    val selectedEmoji: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val isLoading: Boolean = false
)

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()
    
    fun updateAmount(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }
    
    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }
    
    fun selectCategory(category: String, emoji: String, type: TransactionType) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            selectedEmoji = emoji,
            transactionType = type
        )
    }
    
    fun addTransaction(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.amount.isBlank() || state.selectedCategory.isBlank()) return
        
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)
            
            val transaction = Transaction(
                amount = state.amount.toDoubleOrNull() ?: 0.0,
                category = state.selectedCategory,
                description = state.description.ifBlank { state.selectedCategory },
                type = state.transactionType,
                date = Date(),
                emoji = state.selectedEmoji
            )
            
            repository.insertTransaction(transaction)
            onSuccess()
        }
    }
}