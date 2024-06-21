package com.example.appmusickotlin.ui.ListenMusic

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.ActivityListenMusicBinding
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.service.ForegroundService
import com.example.appmusickotlin.ui.home.viewmodel.HomeViewModel
import com.example.appmusickotlin.util.formatDuration.formatDuration

class ListenMusicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListenMusicBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var currentPositionObserver: Observer<Int>
    private lateinit var currentPlayObserver : Observer<Boolean>
    private lateinit var foregroundService: ForegroundService

    private var play = true


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as ForegroundService.LocalBinder
            foregroundService = binder.getService()
            // Quan sát currentPosition từ ForegroundService
            foregroundService.currentPosition.observe(this@ListenMusicActivity, currentPositionObserver)
            foregroundService.isPrepared.observe(this@ListenMusicActivity, currentPlayObserver)

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // Xử lý khi mất kết nối với Service
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListenMusicBinding.inflate(layoutInflater)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        bindService(Intent(this, ForegroundService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)



        val song = intent.getSerializableExtra("song") as? Song

        currentPositionObserver = Observer { newPosition ->
            binding.sbMusic.progress = newPosition
            binding.txtNumberPlay.text = newPosition.toLong().formatDuration()
        }

        binding.sbMusic.max = song!!.duration!!.toInt()
        binding.txtMusic.text = song.name
        binding.txtArtist.text = song.artist
        binding.txtNumberEnd.text = song.duration!!.formatDuration()


        currentPlayObserver = Observer { isPlaying ->
            play = isPlaying
            changePlayMusic()
        }

        binding.btnPlayMusic.setOnClickListener {
            if (play) {
                val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                    action = "ACTION_PAUSE"
                }
                startService(serviceIntent)
            } else {
                val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                    action = "ACTION_PLAY"
                }
                startService(serviceIntent)

            }
            changePlayMusic()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnClose.setOnClickListener {

            val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                action = "ACTION_CLOSE"
            }
            startService(serviceIntent)
            finish()
        }


        setContentView(binding.root)

    }
    private fun changePlayMusic() {
        if (play == false ) {
            binding.btnPlayMusic.setImageResource(R.drawable.ic_player)
        } else {
            binding.btnPlayMusic.setImageResource(R.drawable.ic_pause)
        }
    }

}