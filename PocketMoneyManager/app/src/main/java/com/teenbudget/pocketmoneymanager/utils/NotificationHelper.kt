package com.teenbudget.pocketmoneymanager.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.teenbudget.pocketmoneymanager.R

object NotificationHelper {
    private const val CHANNEL_ID = "pocket_money_notifications"
    private const val CHANNEL_NAME = "Pocket Money Notifications"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for pocket money management"
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showBudgetWarning(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("💸 Your wallet is crying!")
            .setContentText("You're spending too much this week. Slow down!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
            
        notificationManager.notify(1, notification)
    }

    fun showStreakNotification(context: Context, streak: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("🔥 You're on fire!")
            .setContentText("You're on a $streak-day savings streak, keep it up!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
            
        notificationManager.notify(2, notification)
    }
}