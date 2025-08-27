package com.teenbudget.pocketmoneymanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teenbudget.pocketmoneymanager.ui.theme.NeonTeal

@Composable
fun CategorySelector(
    emoji: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(
                if (isSelected) NeonTeal.copy(alpha = 0.2f) 
                else MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
            .padding(12.dp)
    ) {
        Text(
            text = emoji,
            fontSize = 32.sp
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}