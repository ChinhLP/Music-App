package com.example.appmusickotlin.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.controller.ControllerImpl

class SigInScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sig_in_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * khai bao bien chung
         */
        val btnButton = findViewById<Button>(R.id.button)
        val txtSignUpTextView = findViewById<TextView>(R.id.textView3)


        /**
         * xu ly khi click vao button
         */
        btnButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextText1)
            val password = findViewById<EditText>(R.id.editTextText2)
            val controllerImpl = ControllerImpl()
            val text = controllerImpl.SignIn(email.text.toString(), password.text.toString())
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

            /**
             * chuyen sang man hinh home
             */

            if (text == "Đăng nhập thành công"){
                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)
            }

        }
        /**
         * xu ly khi click vao txtSignUpTextView
         */
        txtSignUpTextView.setOnClickListener {
            val intent = Intent(this, SigupScreenActivity::class.java)
            startActivity(intent)        }


    }
}