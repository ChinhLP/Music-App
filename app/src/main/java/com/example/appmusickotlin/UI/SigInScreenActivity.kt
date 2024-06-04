package com.example.appmusickotlin.UI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.model.myUser

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

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextText1)
            val password = findViewById<EditText>(R.id.editTextText2)
            if(email.text.toString() != myUser.email || password.text.toString() != myUser.password ){
                Toast.makeText(this, "email hoac password sai ", Toast.LENGTH_SHORT).show()

            }
        }

    }
}