package com.example.appmusickotlin.ui.ListenMusic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.ActivityListenMusicBinding

class ListenMusicActivity : AppCompatActivity() {
    private lateinit var binding :ActivityListenMusicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListenMusicBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

    }


}