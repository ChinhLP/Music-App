package com.example.appmusickotlin.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startForegroundService
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.ListenMusic.ListenMusicActivity
import com.example.appmusickotlin.ui.home.HomeScreenActivity

class NotificationManager(private val context: Context) {

    private val CHANNEL_ID = "MusicPlayerChannel"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for foreground music player service"
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)

            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun buildNotification(contentText: String): Notification {

        val notificationIntent = Intent(context, ListenMusicActivity::class.java)

        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        // bao bọc notificationIntent và có thể được sử dụng để khởi chạy ListenMusicActivity khi người dùng nhấn vào thông báo
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Media Service")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_previous, "Previous", null)
            .addAction(R.drawable.ic_play, "Play", null)
            .addAction(R.drawable.ic_next, "Next", null)
            .addAction(R.drawable.ic_remove, "Cancel", null)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .build()
    }
}