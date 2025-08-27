package com.teenbudget.pocketmoneymanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val category: String,
    val description: String,
    val type: TransactionType,
    val date: Date,
    val emoji: String
)

enum class TransactionType {
    INCOME, EXPENSE
}

enum class ExpenseCategory(val emoji: String, val displayName: String) {
    FOOD("🍕", "Food"),
    GAMES("🎮", "Games"),
    TRAVEL("🚖", "Travel"),
    HANGOUTS("🎉", "Hangouts"),
    SHOPPING("🎁", "Shopping"),
    ENTERTAINMENT("🎬", "Entertainment"),
    EDUCATION("📚", "Education"),
    OTHER("💰", "Other")
}

enum class IncomeCategory(val emoji: String, val displayName: String) {
    POCKET_MONEY("💵", "Pocket Money"),
    GIFT("🎁", "Gift"),
    ALLOWANCE("💰", "Allowance"),
    PART_TIME("💼", "Part-time Job"),
    OTHER("💸", "Other")
}