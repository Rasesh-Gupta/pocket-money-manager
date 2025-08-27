package com.teenbudget.pocketmoneymanager.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teenbudget.pocketmoneymanager.data.ExpenseCategory
import com.teenbudget.pocketmoneymanager.data.IncomeCategory
import com.teenbudget.pocketmoneymanager.ui.components.AnimatedWallet
import com.teenbudget.pocketmoneymanager.ui.components.BudgetChart
import com.teenbudget.pocketmoneymanager.ui.components.SavingsGoalCard
import com.teenbudget.pocketmoneymanager.ui.theme.*
import com.teenbudget.pocketmoneymanager.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
            .padding(16.dp)
    ) {
        // Header with greeting
        Text(
            text = "Hey ${uiState.userName}! 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        // Balance Card with Animated Wallet
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedWallet()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Current Balance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "$${String.format("%.2f", uiState.balance)}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp
                    ),
                    color = NeonTeal
                )
            }
        }
        
        // Quick Action Buttons
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(ExpenseCategory.values()) { category ->
                QuickActionButton(
                    emoji = category.emoji,
                    label = category.displayName,
                    onClick = { 
                        viewModel.setSelectedCategory(category.displayName)
                        navController.navigate("add_transaction")
                    }
                )
            }
        }
        
        // Income buttons
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(IncomeCategory.values()) { category ->
                QuickActionButton(
                    emoji = category.emoji,
                    label = category.displayName,
                    onClick = { 
                        viewModel.setSelectedCategory(category.displayName)
                        navController.navigate("add_transaction")
                    },
                    isIncome = true
                )
            }
        }
        
        // Budget Chart
        BudgetChart(
            weeklySpent = uiState.weeklySpent,
            weeklyBudget = uiState.weeklyBudget,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        // Active Savings Goals
        if (uiState.activeSavingsGoals.isNotEmpty()) {
            Text(
                text = "Active Goals 🎯",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.activeSavingsGoals.take(3)) { goal ->
                    SavingsGoalCard(
                        goal = goal,
                        onClick = { navController.navigate("goals") }
                    )
                }
            }
        }
        
        // Gamification Stats
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = ElectricPurple.copy(alpha = 0.1f)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("🪙", uiState.totalCoins.toString(), "Coins")
                StatItem("🔥", uiState.currentStreak.toString(), "Streak")
                StatItem("🏆", uiState.unlockedBadges.toString(), "Badges")
            }
        }
    }
}

@Composable
fun QuickActionButton(
    emoji: String,
    label: String,
    onClick: () -> Unit,
    isIncome: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    if (isIncome) SuccessGreen.copy(alpha = 0.2f) 
                    else CoralOrange.copy(alpha = 0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 24.sp
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun StatItem(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 24.sp)
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}