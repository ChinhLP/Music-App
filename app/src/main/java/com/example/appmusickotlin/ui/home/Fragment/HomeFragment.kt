package com.example.appmusickotlin.ui.home.Fragment

import android.content.BroadcastReceiver
import android.content.Context
import com.example.appmusickotlin.service.MusicService
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.appmusickotlin.ui.authetication.SigInScreenActivity
import com.example.appmusickotlin.viewModel.MediaViewModel
import com.example.appmusickotlin.databinding.FragmentHomefragmentBinding
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomefragmentBinding



    private val mediaViewModel: MediaViewModel by activityViewModels()


    private var isPlay: Boolean = false


    // sử dung broadcastReceiver
    private val playBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isPlay = intent?.getBooleanExtra("isPlaying", false) ?: false
            updatePlayPauseButton()
        }
    }
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.example.syncbuttons.PLAY_ACTION")
        requireContext().registerReceiver(playBroadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(playBroadcastReceiver)
    }

    private fun sendPlayStateToActivity() {
        val intent = Intent("com.example.syncbuttons.PLAY_ACTION")
        intent.putExtra("isPlaying", isPlay)
        requireContext().sendBroadcast(intent)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomefragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaViewModel.isPlaying.observe(this.viewLifecycleOwner, Observer { isPlaying ->
            isPlay = isPlaying
            updatePlayPauseButton()
        })

        BtnTheme()

        btnLw()

        btnBack()

        btnPlayMusic()

        btnPauseMusic()

        btnNext()

        btnPrevious()


    }

    private fun BtnTheme() {
        binding.btnTheme.setOnClickListener {

            // Kiểm tra chủ đề hiện tại của thiết bị
            val uiMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            // Nếu đang ở chủ đề tối, chuyển sang chủ đề sáng và ngược lại
            val newMode = if (uiMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.MODE_NIGHT_NO // Chủ đề sáng
            } else {
                AppCompatDelegate.MODE_NIGHT_YES // Chủ đề tối
            }

            // Đặt chế độ chủ đề mới cho thiết bị
            AppCompatDelegate.setDefaultNightMode(newMode)
        }
    }
    private fun btnBack() {
        binding.btnBack.setOnClickListener {

            val intent = Intent(requireContext(), SigInScreenActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }
    }
    private fun btnLw() {
        binding.btnLw.setOnClickListener {


            // Lấy ngôn ngữ hiện tại của thiết bị
            val currentLocale = Locale.getDefault()
            val currentLanguage = currentLocale.language
            var locale = Locale.getDefault()

            if (currentLanguage == "en") {
                // Chuyển từ tiếng Anh sang tiếng Việt
                Locale.setDefault(Locale("vi"))
                locale = Locale("vi")

            } else if (currentLanguage == "vi") {
                // Chuyển từ tiếng Việt sang tiếng Anh
                Locale.setDefault(Locale("en"))
                locale = Locale("en")

            }

            // Cập nhật cấu hình ngôn ngữ của tài nguyên
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            requireActivity().recreate() // Tái khởi động Activity để áp dụng thay đổi ngôn ngữ

        }
    }
    private fun btnPlayMusic() {
        binding.btnPlayMusic.setOnClickListener {

            isPlay = !isPlay
            updatePlayPauseButton()
            sendPlayStateToActivity()
            // Nếu src hiện tại không phải là ic_play, chuyển sang src ic_play
            val resumeIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_RESUME"
            }

            requireContext().startService(resumeIntent)
            mediaViewModel.setPlayingState(true)


        }
    }

    private fun btnPauseMusic() {
        binding.btnPauseMusic.setOnClickListener {
            // Tạo Intent với hành động là "pause"
            val pauseIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_PAUSE"
            }
            // Gửi Intent đến dịch vụ
            requireContext().startService(pauseIntent)
            mediaViewModel.setPlayingState(false)


        }
    }

    private fun btnNext() {
        binding.btnNext.setOnClickListener {
            // Tạo Intent với hành động là "stop"
            val nextIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_NEXT"

            }
            requireContext().startService(nextIntent)
            mediaViewModel.setPlayingState(true)

        }
    }

    private fun btnPrevious() {
        binding.btnPrevious.setOnClickListener {

            // Tạo Intent với hành động là "stop"
            val backIntent = Intent(requireContext(), MusicService::class.java).apply {
                action = "ACTION_BACK"
            }

            requireContext().startService(backIntent)
            mediaViewModel.setPlayingState(true)

        }
    }

    private fun updatePlayPauseButton() {
        if (isPlay == true) {
            binding.btnPlayMusic.visibility = View.GONE
            binding.btnPauseMusic.visibility = View.VISIBLE

        } else {
            binding.btnPlayMusic.visibility = View.VISIBLE
            binding.btnPauseMusic.visibility = View.GONE

        }
    }


}


