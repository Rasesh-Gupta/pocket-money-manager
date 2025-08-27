package com.teenbudget.pocketmoneymanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import com.teenbudget.pocketmoneymanager.data.Badge
import com.teenbudget.pocketmoneymanager.data.BadgeSystem
import com.teenbudget.pocketmoneymanager.ui.theme.*
import com.teenbudget.pocketmoneymanager.ui.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
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
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Profile 👤",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            IconButton(onClick = { /* Settings */ }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(NeonTeal.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        fontSize = 40.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = uiState.userName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Budget Master",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("🪙", uiState.totalCoins.toString(), "Coins")
            StatCard("🔥", uiState.currentStreak.toString(), "Streak")
            StatCard("🏆", uiState.unlockedBadges.toString(), "Badges")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Badges Section
        Text(
            text = "Badges Collection 🏆",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(BadgeSystem.badges) { badge ->
                BadgeCard(
                    badge = badge,
                    isUnlocked = uiState.totalCoins >= badge.coinsRequired
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Settings
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Settings ⚙️",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                SettingItem("🌙", "Dark Mode", uiState.isDarkMode) { 
                    viewModel.toggleDarkMode() 
                }
                SettingItem("🔔", "Notifications", uiState.notificationsEnabled) { 
                    viewModel.toggleNotifications() 
                }
            }
        }
    }
}

@Composable
fun StatCard(emoji: String, value: String, label: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
}

@Composable
fun BadgeCard(badge: Badge, isUnlocked: Boolean) {
    Card(
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) 
                SuccessGreen.copy(alpha = 0.2f) 
            else 
                MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = badge.emoji,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = badge.title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isUnlocked) "Unlocked!" else "${badge.coinsRequired} coins",
                style = MaterialTheme.typography.labelSmall,
                color = if (isUnlocked) SuccessGreen else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun SettingItem(
    emoji: String,
    title: String,
    isEnabled: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Switch(
            checked = isEnabled,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = NeonTeal,
                checkedTrackColor = NeonTeal.copy(alpha = 0.5f)
            )
        )
    }
}