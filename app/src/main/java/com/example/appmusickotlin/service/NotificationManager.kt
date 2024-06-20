package com.example.appmusickotlin.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.ListenMusic.ListenMusicActivity
import com.example.appmusickotlin.ui.home.HomeScreenActivity

class NotificationManager(private val context: Context) {
    private val CHANNEL_ID = "MusicPlayerChannel"
    private val NOTIFICATION_ID = 1

    fun buildNotification(contentText: String): Notification {
        // Tạo Intent để mở ứng dụng khi người dùng nhấn vào Notification
        val notificationIntent = Intent(context, ListenMusicActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Xây dựng notification
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

//    fun createNotificationChannel() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Music Player Channel",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            val notificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    fun showNotification(notification: Notification) {
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(NOTIFICATION_ID, notification)
//    }
//
//    fun cancelNotification() {
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.cancel(NOTIFICATION_ID)
//    }
}