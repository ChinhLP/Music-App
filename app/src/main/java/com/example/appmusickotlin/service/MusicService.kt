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
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.appmusickotlin.R

 class MusicService : Service() {

     private var mediaPlayer: MediaPlayer? = null
     private val channelId = "MusicServiceChannel"
     private val songList = listOf(R.raw.kemduyen, R.raw.rrr)


     override fun onBind(intent: Intent): IBinder? {
         return null
     }

     override fun onCreate() {
         super.onCreate()
         createNotificationChannel()

         // Khởi tạo MediaPlayer với đường dẫn tới file nhạc trên thiết bị
         mediaPlayer = MediaPlayer.create(this, R.raw.kemduyen)
         mediaPlayer?.isLooping = true


     }

     override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         startForeground()

         when (intent?.action) {
             "ACTION_PAUSE" -> {
                 // Thực hiện hành động tạm dừng phát nhạc
                 mediaPlayer?.pause()
             }
             "ACTION_RESUME" -> {
                 // Thực hiện hành động tiếp tục phát nhạc
                 mediaPlayer?.start()
             }
             // Xử lý các hành động khác nếu cần
         }
         // Không gọi startForeground ở đây nếu bạn không muốn dịch vụ trở thành foreground service khi được gọi từ các nút này.
         return START_STICKY
     }

     override fun onDestroy() {
         super.onDestroy()
         mediaPlayer?.stop()
         mediaPlayer?.release()
     }

     private fun startForeground() {

// Định nghĩa hành động cho nút Play
         val playIntent = Intent(this, MusicService::class.java).apply {
             action = "ACTION_RESUME"
         }
         val playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)

// Định nghĩa hành động cho nút Pause
         val pauseIntent = Intent(this, MusicService::class.java).apply {
             action = "ACTION_PAUSE"
         }
         val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

// Tạo một RemoteViews để ánh xạ đến layout tùy chỉnh
         val notificationLayout = RemoteViews(packageName, R.layout.notification_layout)



         //notificationLayout.setTextViewText(R.id.tvTitle, R.raw.kemduyen)


// Gán PendingIntent cho các button trong layout tùy chỉnh
         notificationLayout.setOnClickPendingIntent(R.id.btnPlay, playPendingIntent)
         notificationLayout.setOnClickPendingIntent(R.id.btnPause, pausePendingIntent)

// Định cấu hình thông báo với RemoteViews tùy chỉnh
         val notification = NotificationCompat.Builder(this, channelId)
             .setSmallIcon(R.drawable.ic_playlist)
             .setContent(notificationLayout) // Sử dụng RemoteViews tùy chỉnh
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
}