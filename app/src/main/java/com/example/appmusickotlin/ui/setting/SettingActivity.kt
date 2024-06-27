package com.example.appmusickotlin.ui.setting

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.ActivitySettingBinding
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import java.util.Locale

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imbBack.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.imbOk.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        //ngăn không cho back về bằng nút
        this.onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })


        binding.txtSetLanguage.setOnClickListener {
            val popup = PopupMenu(this, binding.txtSetLanguage, Gravity.END)
            popup.menuInflater.inflate(R.menu.menu_popup_laguage, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                val current_Locale = Locale.getDefault()
                val current_Language = current_Locale.language

                when (menuItem.itemId) {
                    R.id.vietnamese -> {
                        var locale = Locale.getDefault()
                        if(current_Language != "vi"){
                            Locale.setDefault(Locale("vi"))
                            locale = Locale("vi")
                            // Cập nhật cấu hình ngôn ngữ của tài nguyên
                            val config = Configuration()
                            config.locale = locale
                            resources.updateConfiguration(config, resources.displayMetrics)
                            this.recreate()
                        }

                        true

                    }

                    R.id.korean -> {

                        var locale = Locale.getDefault()
                        if(current_Language != "ko"){
                            Locale.setDefault(Locale("ko"))
                            locale = Locale("ko")
                        }
                        binding.txtSetLanguage.text = "korean"

                        // Cập nhật cấu hình ngôn ngữ của tài nguyên
                        val config = Configuration()
                        config.locale = locale
                        resources.updateConfiguration(config, resources.displayMetrics)
                        this.recreate()
                        true
                    }

                    R.id.english -> {
                        var locale = Locale.getDefault()
                        if(current_Language != "en"){
                            Locale.setDefault(Locale("en"))
                            locale = Locale("en")
                        }
                        binding.txtSetLanguage.text = "English"

                        // Cập nhật cấu hình ngôn ngữ của tài nguyên
                        val config = Configuration()
                        config.locale = locale
                        resources.updateConfiguration(config, resources.displayMetrics)
                        this.recreate()
                        true
                    }

                    R.id.french -> {

                        var locale = Locale.getDefault()
                        if(current_Language != "fr"){
                            Locale.setDefault(Locale("fr"))
                            locale = Locale("fr")
                        }
                        binding.txtSetLanguage.text = "french"

                        // Cập nhật cấu hình ngôn ngữ của tài nguyên
                        val config = Configuration()
                        config.locale = locale
                        resources.updateConfiguration(config, resources.displayMetrics)
                        this.recreate()
                        true
                    }

                    else -> false
                }
            }

            // Hiển thị PopupMenu
            popup.show()
        }

    }
//    fun getCurrentLanguage(context: Context) {
//        val currentLocale = Locale.getDefault()
//        val currentLanguage = currentLocale.language
//        var locale = Locale.getDefault()
//        if (currentLanguage == "en") {
//            // Chuyển từ tiếng Anh sang tiếng Việt
//            Locale.setDefault(Locale("vi"))
//            locale = Locale("vi")
//
//        } else if (currentLanguage == "vi") {
//            // Chuyển từ tiếng Việt sang tiếng Anh
//            Locale.setDefault(Locale("en"))
//            locale = Locale("en")
//
//        }
//
//        // Cập nhật cấu hình ngôn ngữ của tài nguyên
//        val config = Configuration()
//        config.locale = locale
//        resources.updateConfiguration(config, resources.displayMetrics)
//        this.recreate()
//
//
//    }

}