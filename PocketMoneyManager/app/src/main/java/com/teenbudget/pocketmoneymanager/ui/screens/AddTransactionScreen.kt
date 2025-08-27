package com.teenbudget.pocketmoneymanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teenbudget.pocketmoneymanager.data.ExpenseCategory
import com.teenbudget.pocketmoneymanager.data.IncomeCategory
import com.teenbudget.pocketmoneymanager.data.TransactionType
import com.teenbudget.pocketmoneymanager.ui.components.CategorySelector
import com.teenbudget.pocketmoneymanager.ui.theme.*
import com.teenbudget.pocketmoneymanager.ui.viewmodels.AddTransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Add Transaction") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Transaction Type Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    onClick = { selectedType = TransactionType.EXPENSE },
                    label = { Text("💸 Expense") },
                    selected = selectedType == TransactionType.EXPENSE,
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    onClick = { selectedType = TransactionType.INCOME },
                    label = { Text("💰 Income") },
                    selected = selectedType == TransactionType.INCOME,
                    modifier = Modifier.weight(1f)
                )
            }

            // Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                leadingIcon = { Text("$", style = MaterialTheme.typography.titleLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Category Selection
            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedType == TransactionType.EXPENSE) {
                    items(ExpenseCategory.values()) { category ->
                        CategorySelector(
                            emoji = category.emoji,
                            label = category.displayName,
                            isSelected = selectedCategory == category.displayName,
                            onClick = {
                                selectedCategory = category.displayName
                                selectedEmoji = category.emoji
                            }
                        )
                    }
                } else {
                    items(IncomeCategory.values()) { category ->
                        CategorySelector(
                            emoji = category.emoji,
                            label = category.displayName,
                            isSelected = selectedCategory == category.displayName,
                            onClick = {
                                selectedCategory = category.displayName
                                selectedEmoji = category.emoji
                            }
                        )
                    }
                }
            }

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            // Add Button
            Button(
                onClick = {
                    if (amount.isNotBlank() && selectedCategory.isNotBlank()) {
                        viewModel.addTransaction(
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            category = selectedCategory,
                            description = description.ifBlank { selectedCategory },
                            type = selectedType,
                            emoji = selectedEmoji
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == TransactionType.EXPENSE) 
                        CoralOrange else SuccessGreen
                )
            ) {
                Text(
                    text = if (selectedType == TransactionType.EXPENSE) 
                        "Add Expense 💸" else "Add Income 💰",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}