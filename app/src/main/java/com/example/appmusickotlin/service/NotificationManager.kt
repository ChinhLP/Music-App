package com.example.appmusickotlin.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startForegroundService
import com.example.appmusickotlin.R
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.ui.ListenMusic.ListenMusicActivity
import com.example.appmusickotlin.ui.Splash.SplashActivity
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

    fun buildNotification(song : Song?,currentIndex : Int? , maxIndex : Int?,isPrepared : Boolean): Notification {

        val notificationLayout = RemoteViews(context.packageName, R.layout.notification_layout)

        notificationLayout.setInt(
            R.id.notification_layout,
            "setBackgroundColor",
            Color.parseColor("#464646")
        )

        // Set text and image in the RemoteViews
        notificationLayout.setTextViewText(R.id.txtApp, "Music App")
        notificationLayout.setTextViewText(R.id.txtNameMusic, song!!.name)
        notificationLayout.setTextViewText(R.id.txtArtist,song.artist)
        notificationLayout.setTextViewText(R.id.txtNumberCurrentMusic, (currentIndex!! + 1).toString())
        notificationLayout.setTextViewText(R.id.txtFullNumber, maxIndex.toString())

        if(isPrepared) {
            notificationLayout.setImageViewResource(R.id.imvPlay,R.drawable.ic_pause)

        } else {
            notificationLayout.setImageViewResource(R.id.imvPlay,R.drawable.ic_play)

        }


// Create intents for each action
        val playIntent = Intent(context, ForegroundService::class.java).apply { action = "ACTION_PLAY" }
        val pauseIntent = Intent(context, ForegroundService::class.java).apply { action = "ACTION_PAUSE" }
        val nextIntent = Intent(context, ForegroundService::class.java).apply { action = "ACTION_NEXT" }
        val previousIntent = Intent(context, ForegroundService::class.java).apply { action = "ACTION_PREVIOUS" }
        val closeIntent = Intent(context, ForegroundService::class.java).apply { action = "ACTION_CLOSE" }

        // Create pending intents for each action
        val playPendingIntent = PendingIntent.getService(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent = PendingIntent.getService(context, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val nextPendingIntent = PendingIntent.getService(context, 2, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val previousPendingIntent = PendingIntent.getService(context, 3, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val closePendingIntent = PendingIntent.getService(context,4, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        // Set click listeners in the RemoteViews
        if(isPrepared){
            notificationLayout.setOnClickPendingIntent(R.id.imvPlay, pausePendingIntent)
        } else {
            notificationLayout.setOnClickPendingIntent(R.id.imvPlay, playPendingIntent)
        }
        notificationLayout.setOnClickPendingIntent(R.id.imvNext, nextPendingIntent)
        notificationLayout.setOnClickPendingIntent(R.id.imvPrevious, previousPendingIntent)
        notificationLayout.setOnClickPendingIntent(R.id.imvDelete,closePendingIntent)

        // Load custom icon (optional)
        val customIcon = BitmapFactory.decodeResource(context.resources, R.drawable.avatas)
        notificationLayout.setImageViewBitmap(R.id.imvMusicImage, customIcon)

        // Set up intent for when notification is clicked
        val notificationIntent = Intent(context, SplashActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // Default icon in status bar
            .setContentIntent(pendingIntent)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayout)
            .setAutoCancel(true) // Dismiss notification on click

        return builder.build()
    }
}