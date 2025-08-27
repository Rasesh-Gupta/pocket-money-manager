package com.teenbudget.pocketmoneymanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teenbudget.pocketmoneymanager.data.SavingsGoal
import com.teenbudget.pocketmoneymanager.ui.theme.*
import com.teenbudget.pocketmoneymanager.ui.viewmodels.GoalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    navController: NavController,
    viewModel: GoalsViewModel = hiltViewModel()
) {
    val goals by viewModel.goals.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Savings Goals 🎯",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(goals) { goal ->
                    GoalCard(
                        goal = goal,
                        onAddMoney = { amount -> viewModel.addMoneyToGoal(goal.id, amount) }
                    )
                }
            }
        }
        
        FloatingActionButton(
            onClick = { /* Add new goal */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = NeonTeal
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Goal")
        }
    }
}

@Composable
fun GoalCard(
    goal: SavingsGoal,
    onAddMoney: (Double) -> Unit
) {
    val progress = (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${goal.emoji} ${goal.title}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                if (progress >= 1.0f) {
                    Text(
                        text = "🎉 COMPLETED!",
                        style = MaterialTheme.typography.labelLarge,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = goal.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = if (progress >= 1.0f) SuccessGreen else NeonTeal,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${String.format("%.0f", goal.currentAmount)} saved",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$${String.format("%.0f", goal.targetAmount)} goal",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            if (progress < 1.0f) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Button(
                    onClick = { onAddMoney(10.0) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonTeal
                    )
                ) {
                    Text("Add $10 💰")
                }
            }
        }
    }
}