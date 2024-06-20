package com.example.appmusickotlin.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.SeekBar
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.util.mediaPlay.MusicPlayer


class ForegroundService : Service() {

    private lateinit var musicPlayer: MusicPlayer
    private lateinit var seekBar: SeekBar


    override fun onCreate() {
        super.onCreate()
        musicPlayer = MusicPlayer(this, null)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1,null)

        when (intent?.action) {
            "ACTION_NEXT" -> handleActionNext(intent)
//            "ACTION_PAUSE" -> handleActionPause()
//            "ACTION_RESUME" -> handleActionPlay()
//            "ACTION_BACK" -> handleActionPrevious()
//            "ACTION_STOP" -> handleActionStop()
            // Xử lý các hành động khác nếu cần
        }
        return START_NOT_STICKY
    }

    fun handleActionNext(intent: Intent?){
//        val song: Song? = intent?.getParcelableExtra("songPlay")
//        musicPlayer.stopAndRelease()
//        musicPlayer.setDataSource(song.path!!)

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }
}