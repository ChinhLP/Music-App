package com.example.appmusickotlin.ui.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.model.User
//import com.example.appmusickotlin.model.getUser
//import com.example.appmusickotlin.model.isLoggedIn
import com.example.appmusickotlin.data.local.db.database.AppDatabase
import com.example.appmusickotlin.model.getUser
import com.example.appmusickotlin.model.isLoggedIn
import com.example.appmusickotlin.ui.authetication.AuthActivity
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.orhanobut.hawk.Hawk

class SplashActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 2000 // 3 seconds



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Hawk.init(this).build()
//

        Handler().postDelayed({

            val isLoggedIn = isLoggedIn()

            if (isLoggedIn) {
                Log.e("SplashActivity", "$isLoggedIn")

                val user = getUser()
                if (user != null) {
                    User.userId = user.userId!!
                    User.username = user.username
                    User.password = user.password
                    User.email = user.email
                    User.imageAvatar = user.imageAvatar
                    //User.albumsLst = user.albumsLst

                    // Điều hướng đến HomeScreenActivity
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("SplashActivity", "Failed to load user data")
                    finish()
                }
            } else {
                // Điều hướng đến AuthActivity (màn hình đăng nhập)
                val intent = Intent(this, AuthActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }, splashTimeOut)

    }

}