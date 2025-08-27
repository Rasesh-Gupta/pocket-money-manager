package com.teenbudget.pocketmoneymanager.di

import android.content.Context
import androidx.room.Room
import com.teenbudget.pocketmoneymanager.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PocketMoneyDatabase {
        return Room.databaseBuilder(
            context,
            PocketMoneyDatabase::class.java,
            "pocket_money_database"
        ).build()
    }

    @Provides
    fun provideTransactionDao(database: PocketMoneyDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideSavingsGoalDao(database: PocketMoneyDatabase): SavingsGoalDao {
        return database.savingsGoalDao()
    }

    @Provides
    fun provideUserProfileDao(database: PocketMoneyDatabase): UserProfileDao {
        return database.userProfileDao()
    }
}