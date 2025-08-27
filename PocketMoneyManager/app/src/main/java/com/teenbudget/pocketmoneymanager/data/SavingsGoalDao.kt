package com.teenbudget.pocketmoneymanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingsGoalDao {
    @Query("SELECT * FROM savings_goals ORDER BY createdDate DESC")
    fun getAllSavingsGoals(): Flow<List<SavingsGoal>>

    @Query("SELECT * FROM savings_goals WHERE isCompleted = 0 ORDER BY createdDate DESC")
    fun getActiveSavingsGoals(): Flow<List<SavingsGoal>>

    @Query("SELECT * FROM savings_goals WHERE id = :id")
    suspend fun getSavingsGoalById(id: Long): SavingsGoal?

    @Insert
    suspend fun insertSavingsGoal(savingsGoal: SavingsGoal)

    @Update
    suspend fun updateSavingsGoal(savingsGoal: SavingsGoal)

    @Delete
    suspend fun deleteSavingsGoal(savingsGoal: SavingsGoal)
}