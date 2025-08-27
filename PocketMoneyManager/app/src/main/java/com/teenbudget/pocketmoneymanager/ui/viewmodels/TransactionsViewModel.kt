package com.teenbudget.pocketmoneymanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teenbudget.pocketmoneymanager.data.Transaction
import com.teenbudget.pocketmoneymanager.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    
    val transactions: StateFlow<List<Transaction>> = repository.getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}