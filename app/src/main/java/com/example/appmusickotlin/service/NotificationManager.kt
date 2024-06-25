package com.example.appmusickotlin.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startForegroundService
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.ListenMusic.ListenMusicActivity
import com.example.appmusickotlin.ui.home.HomeScreenActivity

class NotificationManager(private val context: Context) {

    private lateinit var mediaSession: MediaSessionCompat

    private val CHANNEL_ID = "MusicPlayerChannel"

    init {
        createNotificationChannel()
        initializeMediaSession()

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

    fun buildNotification(context: Context, contentText: String): Notification {
        val notificationIntent = Intent(context, ListenMusicActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Load large icon from drawable
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.avatas)

        // Build notification using NotificationCompat.Builder
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setSubText("Tincoder")
            .setContentTitle("Media Service")
            .setContentText(contentText)
            .setLargeIcon(largeIcon)
            .addAction(R.drawable.ic_previous, "Previous", null)
            .addAction(R.drawable.ic_play, "Play", null)
            .addAction(R.drawable.ic_next, "Next", null)
            .addAction(R.drawable.ic_delete, "Cancel", null)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView( 0,1,2).setMediaSession(mediaSession.sessionToken) // Show previous, play, next buttons in compact view
            )

//            .setContentIntent(pendingIntent)

            .build()
    }
    private fun initializeMediaSession() {
        mediaSession = MediaSessionCompat(context, "MusicPlayerSession")
    }
}