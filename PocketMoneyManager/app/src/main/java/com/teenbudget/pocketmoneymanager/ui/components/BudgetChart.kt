package com.teenbudget.pocketmoneymanager.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teenbudget.pocketmoneymanager.ui.theme.*

@Composable
fun BudgetChart(
    weeklySpent: Double,
    weeklyBudget: Double,
    modifier: Modifier = Modifier
) {
    val progress = (weeklySpent / weeklyBudget).coerceIn(0.0, 1.0).toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000), label = "progress"
    )
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Weekly Budget 📊",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                // Background
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRoundRect(
                        color = Color.Gray.copy(alpha = 0.3f),
                        size = size,
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx())
                    )
                }
                
                // Progress
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val progressWidth = size.width * animatedProgress
                    if (progressWidth > 0) {
                        drawRoundRect(
                            brush = Brush.horizontalGradient(
                                colors = if (progress > 0.8f) {
                                    listOf(WarningOrange, ErrorRed)
                                } else {
                                    listOf(NeonTeal, LimeGreen)
                                }
                            ),
                            size = androidx.compose.ui.geometry.Size(progressWidth, size.height),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx())
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${String.format("%.0f", weeklySpent)} spent",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "$${String.format("%.0f", weeklyBudget)} budget",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}