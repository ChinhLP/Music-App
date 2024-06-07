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
import androidx.core.view.isVisible
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.FragmentHomefragmentBinding



private lateinit var binding: FragmentHomefragmentBinding

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomefragmentBinding.inflate(inflater, container, false)

        binding.btnPlayMusic.setOnClickListener {


                // Nếu src hiện tại không phải là ic_play, chuyển sang src ic_play
                val resumeIntent = Intent(requireContext(), MusicService::class.java).apply {
                    action = "ACTION_RESUME"
                }
            binding.btnPlayMusic.visibility = View.GONE
            binding.btnPauseMusic.visibility = View.VISIBLE
                requireContext().startService(resumeIntent)



        }

        binding.btnPauseMusic.setOnClickListener {
            // Tạo Intent với hành động là "pause"
            val pauseIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_PAUSE"
            }
            binding.btnPlayMusic.visibility = View.VISIBLE
            binding.btnPauseMusic.visibility = View.GONE
            // Gửi Intent đến dịch vụ
            requireContext().startService(pauseIntent)
        }

        binding.btnNext.setOnClickListener {
            // Tạo Intent với hành động là "stop"
            val nextIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_NEXT"

            }
            requireContext().startService(nextIntent)

        }
            binding.btnBack.setOnClickListener {
                // Tạo Intent với hành động là "stop"
                val backIntent = Intent(requireContext(), MusicService::class.java).apply {
                    action = "ACTION_BACK"
                }
                requireContext().startService(backIntent)

            }





        return binding.root
    }

}

