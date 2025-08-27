package com.teenbudget.pocketmoneymanager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teenbudget.pocketmoneymanager.data.SavingsGoal
import com.teenbudget.pocketmoneymanager.ui.theme.NeonTeal

@Composable
fun SavingsGoalCard(
    goal: SavingsGoal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "${goal.emoji} ${goal.title}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = NeonTeal
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "$${String.format("%.0f", goal.currentAmount)} / $${String.format("%.0f", goal.targetAmount)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}