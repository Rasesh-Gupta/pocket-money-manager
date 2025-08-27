package com.teenbudget.pocketmoneymanager.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(
    entities = [Transaction::class, SavingsGoal::class, UserProfile::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PocketMoneyDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun savingsGoalDao(): SavingsGoalDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: PocketMoneyDatabase? = null

        fun getDatabase(context: Context): PocketMoneyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PocketMoneyDatabase::class.java,
                    "pocket_money_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}