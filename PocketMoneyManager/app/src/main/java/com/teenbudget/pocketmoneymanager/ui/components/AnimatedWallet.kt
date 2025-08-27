package com.teenbudget.pocketmoneymanager.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.teenbudget.pocketmoneymanager.ui.theme.NeonTeal

@Composable
fun AnimatedWallet() {
    val infiniteTransition = rememberInfiniteTransition(label = "wallet")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )
    
    Canvas(
        modifier = Modifier.size(80.dp)
    ) {
        drawWallet(scale)
    }
}

private fun DrawScope.drawWallet(scale: Float) {
    val walletColor = NeonTeal
    val size = this.size.minDimension * scale * 0.8f
    val centerX = this.size.width / 2
    val centerY = this.size.height / 2
    
    // Draw wallet body
    drawRoundRect(
        color = walletColor,
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - size/2,
            centerY - size/3
        ),
        size = androidx.compose.ui.geometry.Size(size, size * 0.6f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
    )
    
    // Draw wallet flap
    drawRoundRect(
        color = walletColor.copy(alpha = 0.8f),
        topLeft = androidx.compose.ui.geometry.Offset(
            centerX - size/2.5f,
            centerY - size/2.2f
        ),
        size = androidx.compose.ui.geometry.Size(size * 0.8f, size * 0.3f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx())
    )
}