package com.example.appmusickotlin.ui.ListenMusic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListenMusicBinding.inflate(layoutInflater)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        val song = intent.getSerializableExtra("song") as? Song

        binding.txtMusic.text = song!!.name
        binding.txtArtist.text = song.artist
        binding.txtNumberEnd.text = song.duration!!.formatDuration()

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

}