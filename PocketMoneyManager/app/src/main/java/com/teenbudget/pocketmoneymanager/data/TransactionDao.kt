package com.teenbudget.pocketmoneymanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getTransactionsByDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'INCOME'")
    fun getTotalIncome(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'EXPENSE'")
    fun getTotalExpenses(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'EXPENSE' AND date >= :startDate AND date <= :endDate")
    fun getExpensesInDateRange(startDate: Date, endDate: Date): Flow<Double?>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}