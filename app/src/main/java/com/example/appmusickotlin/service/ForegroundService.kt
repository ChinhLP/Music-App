package com.example.appmusickotlin.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmusickotlin.model.Song


class ForegroundService : Service() {

    private lateinit var musicPlayer: MusicPlayer
    private lateinit var notificationManager: NotificationManager
    val currentPosition: LiveData<Int>
        get() = musicPlayer.currentPosition
    val isPrepared: LiveData<Boolean>
        get() = musicPlayer.isPrepared
    val isMedia: LiveData<Boolean> get() = musicPlayer.isMedia

    private val _song = MutableLiveData<Song>()
    val song: LiveData<Song> get() = _song


    companion object {
        private const val NOTIFICATION_ID = 1
    }


    inner class LocalBinder : Binder() {
        fun getService(): ForegroundService = this@ForegroundService

    }

    // Khai báo một biến để lưu trữ đối tượng Binder
    private val binder = LocalBinder()

    // Phương thức công khai để trả về đối tượng Binder
    override fun onBind(p0: Intent?): IBinder {

        return binder
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
        val song = intent?.getSerializableExtra("song_") as? Song
        _song.value = song!!
        musicPlayer.stopAndRelease()
        musicPlayer.setDataSource(song,null)
    }

    private fun handleActionPause() {
        musicPlayer.pause()
    }

    private fun handleActionPlay() {

        musicPlayer.play()
    }

    private fun handleActionClose() {
        musicPlayer.stopAndRelease()
    }


    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }


}