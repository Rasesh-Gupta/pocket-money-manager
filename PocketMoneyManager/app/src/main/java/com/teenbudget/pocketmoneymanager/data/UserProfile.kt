package com.teenbudget.pocketmoneymanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Long = 1,
    val name: String,
    val totalCoins: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val weeklyBudget: Double = 0.0,
    val monthlyBudget: Double = 0.0,
    val selectedTheme: String = "CyberNeon",
    val isDarkMode: Boolean = false,
    val notificationsEnabled: Boolean = true
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val coinsRequired: Int,
    val isUnlocked: Boolean = false
)

object BadgeSystem {
    val badges = listOf(
        Badge("budget_boss", "Budget Boss", "Stay under budget for 7 days", "👑", 50),
        Badge("savings_samurai", "Savings Samurai", "Save 500 coins", "⚔️", 100),
        Badge("streak_master", "Streak Master", "Maintain 10-day streak", "🔥", 75),
        Badge("goal_crusher", "Goal Crusher", "Complete 3 savings goals", "💪", 150),
        Badge("penny_pincher", "Penny Pincher", "Spend less than 100 in a week", "🪙", 25)
    )
}