package com.example.appmusickotlin.UI.authetication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.UI.home.HomeScreenActivity
import com.example.appmusickotlin.controller.ControllerImpl
import com.example.appmusickotlin.databinding.ActivitySigInScreenBinding

class SigInScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigInScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySigInScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * xu ly khi click vao button
         */
        binding.btnLogin.setOnClickListener {
            val controllerImpl = ControllerImpl()
            val text = controllerImpl.SignIn(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

            /**
             * chuyen sang man hinh home
             */

            if (text == "Đăng nhập thành công"){
                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        /**
         * xu ly khi click vao txtSignUpTextView
         */
        binding.txtSignup.setOnClickListener {
            val intent = Intent(this, SigupScreenActivity::class.java)
            startActivity(intent)        }


    }


    override fun onStart() {
        Log.e("TAG", "onStart: ", )
        super.onStart()

    }
    override fun onResume() {
        Log.e("TAG", "onResume: ", )

        super.onResume()
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Log.e("TAG", "onPause: ", )
        super.onPause()
        Log.e("TAG", "onPause: ", )
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
    }
    override fun onStop() {
        super.onStop()
        Log.e("TAG", "onStop: ", )
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy: ", )
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
}