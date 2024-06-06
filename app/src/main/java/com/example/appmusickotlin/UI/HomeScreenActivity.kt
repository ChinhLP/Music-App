package com.example.appmusickotlin.UI

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
import java.util.Locale

private const val TAG = "HomeScreenActivity"

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

        val resources = this.resources
        val config = resources.configuration
        val locale1 = Locale("en")
        config.setLocale(locale1)

        binding.btnLw.setOnClickListener {
            // Lấy ngôn ngữ hiện tại của thiết bị
            val currentLocale = Locale.getDefault()
            val currentLanguage = currentLocale.language
            var locale = Locale.getDefault()

            if (currentLanguage == "en") {
                // Chuyển từ tiếng Anh sang tiếng Việt
                Locale.setDefault(Locale("vi"))
                locale = Locale("vi")

            } else if(currentLanguage == "vi") {
                // Chuyển từ tiếng Việt sang tiếng Anh
                Locale.setDefault(Locale("en"))
                locale = Locale("en")

            }

            // Cập nhật cấu hình ngôn ngữ của tài nguyên
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            recreate() // Tái khởi động Activity để áp dụng thay đổi ngôn ngữ

        }


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    val fragment = BlankFragment2()

                    val transaction = supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.fragment_container, fragment)

                    transaction.addToBackStack("BlankFragment2")

                    transaction.commit()
                    true
                }

                R.id.btnLibrary -> {
                    val fragment3 = BlankFragment3()

                    // Bắt đầu một FragmentTransaction
                    val transaction = supportFragmentManager.beginTransaction()

                    // Thay đổi Fragment hiện tại (Fragment2) bằng Fragment3
                    transaction.replace(R.id.fragment_container, fragment3)

                    // Thêm transaction vào Back Stack nếu bạn muốn cho phép quay lại Fragment trước đó bằng nút back
                    transaction.addToBackStack("BlankFragment3")

                    // Commit FragmentTransaction
                    transaction.commit()
                    // Xử lý khi nút Library được chọn
                    true
                }

                R.id.btnPlaylist -> {
                    val fragment = BlankFragment1()

                    val transaction = supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.fragment_container, fragment)

                    transaction.addToBackStack("BlankFragment1")

                    transaction.commit()
                    // Xử lý khi nút Playlist được chọn
                    true
                }

                else -> false
            }
        }



        binding.btnBack.setOnClickListener {
            finish()
        }


    }


}
