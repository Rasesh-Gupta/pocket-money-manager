package com.teenbudget.pocketmoneymanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "savings_goals")
data class SavingsGoal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val emoji: String,
    val createdDate: Date,
    val targetDate: Date?,
    val isCompleted: Boolean = false
) {
    val progress: Float
        get() = if (targetAmount > 0) (currentAmount / targetAmount).toFloat().coerceAtMost(1f) else 0f
}