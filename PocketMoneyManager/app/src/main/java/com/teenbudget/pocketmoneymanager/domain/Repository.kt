package com.teenbudget.pocketmoneymanager.domain

import com.teenbudget.pocketmoneymanager.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val savingsGoalDao: SavingsGoalDao,
    private val userProfileDao: UserProfileDao
) {
    // Transaction operations
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()
    
    fun getTransactionsByDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>> =
        transactionDao.getTransactionsByDateRange(startDate, endDate)
    
    fun getTotalIncome(): Flow<Double?> = transactionDao.getTotalIncome()
    
    fun getTotalExpenses(): Flow<Double?> = transactionDao.getTotalExpenses()
    
    fun getExpensesInDateRange(startDate: Date, endDate: Date): Flow<Double?> =
        transactionDao.getExpensesInDateRange(startDate, endDate)
    
    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insertTransaction(transaction)
    
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.deleteTransaction(transaction)

    // Savings Goal operations
    fun getAllSavingsGoals(): Flow<List<SavingsGoal>> = savingsGoalDao.getAllSavingsGoals()
    
    fun getActiveSavingsGoals(): Flow<List<SavingsGoal>> = savingsGoalDao.getActiveSavingsGoals()
    
    suspend fun insertSavingsGoal(savingsGoal: SavingsGoal) = savingsGoalDao.insertSavingsGoal(savingsGoal)
    
    suspend fun updateSavingsGoal(savingsGoal: SavingsGoal) = savingsGoalDao.updateSavingsGoal(savingsGoal)
    
    suspend fun deleteSavingsGoal(savingsGoal: SavingsGoal) = savingsGoalDao.deleteSavingsGoal(savingsGoal)

    // User Profile operations
    fun getUserProfile(): Flow<UserProfile?> = userProfileDao.getUserProfile()
    
    suspend fun insertUserProfile(userProfile: UserProfile) = userProfileDao.insertUserProfile(userProfile)
    
    suspend fun updateUserProfile(userProfile: UserProfile) = userProfileDao.updateUserProfile(userProfile)
    
    suspend fun addCoins(coins: Int) = userProfileDao.addCoins(coins)
    
    suspend fun updateStreak(streak: Int) = userProfileDao.updateStreak(streak)

    // Business logic
    suspend fun getCurrentBalance(): Double {
        val income = transactionDao.getTotalIncome()
        val expenses = transactionDao.getTotalExpenses()
        return 1000.0 // Placeholder - should calculate from actual data
    }
    
    suspend fun getWeeklyExpenses(): Flow<Double> {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = calendar.time
        calendar.add(java.util.Calendar.WEEK_OF_YEAR, 1)
        val endOfWeek = calendar.time
        
        return getExpensesInDateRange(startOfWeek, endOfWeek).map { it ?: 0.0 }
    }
    
    suspend fun addMoneyToGoal(goalId: Long, amount: Double) {
        val goal = savingsGoalDao.getSavingsGoalById(goalId)
        goal?.let {
            val updatedGoal = it.copy(currentAmount = it.currentAmount + amount)
            savingsGoalDao.updateSavingsGoal(updatedGoal)
        }
    }
    
    suspend fun updateDarkMode(isDarkMode: Boolean) {
        val profile = userProfileDao.getUserProfile()
        // Update profile with new dark mode setting
    }
    
    suspend fun updateNotifications(enabled: Boolean) {
        val profile = userProfileDao.getUserProfile()
        // Update profile with new notification setting
    }
}