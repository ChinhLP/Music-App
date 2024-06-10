package com.example.appmusickotlin.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.example.appmusickotlin.R
import com.example.appmusickotlin.viewModel.MediaViewModel
import com.example.appmusickotlin.databinding.FragmentHomefragmentBinding


class MusicService : Service() {


    private var mediaPlayer: MediaPlayer? = null
    private val channelId = "MusicServiceChannel"
    private val songList = listOf(R.raw.kemduyen, R.raw.nenvahoa, R.raw.yeu5)
    private var currentSongIndex = 0
    private var isPlaying: Boolean = false
    private lateinit var mediaViewModel : MediaViewModel



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun setData(newData: Boolean) {
        isPlaying = newData
    }

    override fun onCreate() {
        super.onCreate()


        mediaViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MediaViewModel::class.java)


        createNotificationChannel()

        // Khởi tạo MediaPlayer với đường dẫn tới file nhạc trên thiết bị
        mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex])
        mediaPlayer?.isLooping = true
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        startForeground(intent?.action.toString())

        when (intent?.action) {
            "ACTION_NEXT" -> handleActionNext()
            "ACTION_PAUSE" -> handleActionPause()
            "ACTION_RESUME" -> handleActionPlay()
            "ACTION_BACK" -> handleActionPrevious()
            "ACTION_STOP" -> handleActionStop()
            // Xử lý các hành động khác nếu cần
        }

        // Không gọi startForeground ở đây nếu bạn không muốn dịch vụ trở thành foreground service khi được gọi từ các nút này.
        return START_STICKY
    }


    private fun sendPlayStateBroadcast() {
        val intent = Intent("com.example.syncbuttons.PLAY_ACTION")
        intent.putExtra("isPlaying", isPlaying)
        sendBroadcast(intent)
    }


    private fun handleActionNext() {
        mediaViewModel.setPlayingState(true)
        isPlaying = true
        sendPlayStateBroadcast()
        if (currentSongIndex == songList.size - 1) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            currentSongIndex = 0
            mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex])
            mediaPlayer?.start()
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            currentSongIndex++
            mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex])
            mediaPlayer?.start()
        }

    }
    private fun handleActionPrevious() {
        mediaViewModel.setPlayingState(true)

        isPlaying = true
        sendPlayStateBroadcast()
        if (currentSongIndex == 0) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            currentSongIndex = songList.size - 1
            mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex])
            mediaPlayer?.start()
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            currentSongIndex--
            mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex])
            mediaPlayer?.start()
        }

    }
    private fun handleActionPlay() {

        mediaViewModel.setPlayingState(true)
        isPlaying = true
        sendPlayStateBroadcast()
        // Thực hiện hành động tiếp tục phát nhạc
        mediaPlayer?.start()
    }
    private fun handleActionStop() {
        mediaViewModel.setPlayingState(false)

        isPlaying = false
        sendPlayStateBroadcast()
        stopForeground(true)
        stopSelf()
    }
    private fun handleActionPause() {
        mediaViewModel.setPlayingState(false)

        isPlaying = false
        sendPlayStateBroadcast()
        // Thực hiện hành động tạm dừng phát nhạc
        mediaPlayer?.pause()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    private fun startForeground(action: String) {

        val notificationIntent = Intent(
            this, FragmentHomefragmentBinding::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val nextIntent = Intent(
            this, MusicService::class.java).setAction("ACTION_NEXT")
        val nextPendingIntent =
            PendingIntent.getService(
                this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val previousIntent = Intent(
            this, MusicService::class.java).setAction("ACTION_BACK")
        val previousPendingIntent =
            PendingIntent.getService(this, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val stopIntent = Intent(
            this,MusicService::class.java).setAction("ACTION_STOP")
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val playPauseAction = if (action == "ACTION_PAUSE") {

            NotificationCompat.Action(
                R.drawable.ic_play,
                "Play",
                createPendingIntent("ACTION_RESUME", true)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.ic_pause,
                "Pause",
                createPendingIntent("ACTION_PAUSE", false),

                )
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Media Service")
            .setContentText("Playing media")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_previous, "Previous", previousPendingIntent)
            .addAction(playPauseAction)
            .addAction(R.drawable.ic_next, "Next", nextPendingIntent)
            .addAction(R.drawable.ic_cancer,"cancer",stopPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .build()

        startForeground(1, notification)
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = "Music Service Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.setImportance(NotificationManager.IMPORTANCE_LOW)

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun createPendingIntent(action: String, value: Boolean): PendingIntent {

        val intent = Intent(this, MusicService::class.java).setAction(action)

        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


}
