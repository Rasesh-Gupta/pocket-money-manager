package com.teenbudget.pocketmoneymanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    @Update
    suspend fun updateUserProfile(userProfile: UserProfile)

    @Query("UPDATE user_profile SET totalCoins = totalCoins + :coins WHERE id = 1")
    suspend fun addCoins(coins: Int)

    @Query("UPDATE user_profile SET currentStreak = :streak WHERE id = 1")
    suspend fun updateStreak(streak: Int)

    @Query("UPDATE user_profile SET longestStreak = :streak WHERE id = 1")
    suspend fun updateLongestStreak(streak: Int)
}