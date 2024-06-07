package com.example.appmusickotlin.UI.home.Fragment

import com.example.appmusickotlin.service.MusicService
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.example.appmusickotlin.databinding.FragmentHomefragmentBinding



private lateinit var binding: FragmentHomefragmentBinding

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomefragmentBinding.inflate(inflater, container, false)

        binding.btnPlayMusic.setOnClickListener {


            // Tạo Intent với hành động là "resume"
            val resumeIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_RESUME"
            }
            // Gửi Intent đến dịch vụ
            requireContext().startService(resumeIntent)

        }

        binding.btnPauseMusic.setOnClickListener {
            // Tạo Intent với hành động là "pause"
            val pauseIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_PAUSE"
            }
            // Gửi Intent đến dịch vụ
            requireContext().startService(pauseIntent)
        }




        return binding.root
    }

}

