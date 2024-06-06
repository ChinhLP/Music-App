package com.example.appmusickotlin.UI

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.ActivityHomeScreenBinding
import com.example.appmusickotlin.databinding.ActivitySigupScreenBinding

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



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

        binding.btnBack.setOnClickListener {
            finish()
        }

    }


}
