package com.example.appmusickotlin.service

import android.app.Service
import android.content.Intent
import android.os.IBinder


class ForegroundService : Service() {

    private lateinit var musicPlayer: MusicPlayer
    private lateinit var notificationManager: NotificationManager

    companion object {
        private const val NOTIFICATION_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        musicPlayer = MusicPlayer(this)
        notificationManager = NotificationManager(this)

        // Create and display the foreground notification
        val notification = notificationManager.buildNotification("Service is running")
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_RESUME" -> handleActionResume(intent)
            "ACTION_PAUSE" -> handleActionPause()
            "ACTION_PLAY" -> handleActionPlay()
            "ACTION_CLOSE" -> handleActionClose()
        }
        return START_NOT_STICKY
    }

    private fun handleActionResume(intent: Intent?) {
        val songPath = intent?.getStringExtra("songPlay")
        musicPlayer.stopAndRelease()
        musicPlayer.setDataSource(songPath!!)
    }

    private fun handleActionPause() {
        musicPlayer.pause()
    }

    private fun handleActionPlay() {
        musicPlayer.play()
    }
    private fun handleActionClose(){
        musicPlayer.release()
    }


    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }


}