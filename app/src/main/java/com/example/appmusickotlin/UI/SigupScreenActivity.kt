package com.example.appmusickotlin.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.model.myUser

class SigupScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sigup_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonCheckValidity = findViewById<Button>(R.id.button)


        buttonCheckValidity.setOnClickListener {

            val username = findViewById<EditText>(R.id.editTextText1)
            val email = findViewById<EditText>(R.id.editTextText2)
            val phoneNumber = findViewById<EditText>(R.id.editTextText3)
            val password = findViewById<EditText>(R.id.editTextText4)
            val rePassword = findViewById<EditText>(R.id.editTextText5)

            val user = User(
                username.text.toString(),
                email.text.toString(),
                phoneNumber.text.toString(),
                password.text.toString(),
                rePassword.text.toString()
            )


            val isValidusername = user.validUsername()
            val isValidemail = user.validEmail()
            val isValidphoneNumber = user.validPhoneNumber()
            val isValidpassword = user.validPassword()
            val isValidrePassword = user.validRePassword()

            if (isValidusername == false) {
                Toast.makeText(this, "sai cu phap ten", Toast.LENGTH_SHORT).show()
            }
            if (isValidemail == false) {
                Toast.makeText(this, "sai cu phap email", Toast.LENGTH_SHORT).show()
            }
            if (isValidphoneNumber == false) {
                Toast.makeText(this, "sai cu phap phoneNumber", Toast.LENGTH_SHORT).show()
            }
            if (isValidpassword == false) {
                Toast.makeText(this, "sai cu phap password", Toast.LENGTH_SHORT).show()
            }
            if (isValidrePassword == false) {
                Toast.makeText(this, "RePassword khong trung password", Toast.LENGTH_SHORT).show()
            }

            if(isValidusername == true && isValidemail == true && isValidphoneNumber == true && isValidpassword == true && isValidrePassword == true){
                val intent = Intent(this, SigInScreenActivity::class.java)
                myUser.username = username.text.toString()
                myUser.email = email.text.toString()
                myUser.phoneNumber = phoneNumber.text.toString()
                myUser.password = password.text.toString()
                myUser.rePassword = rePassword.text.toString()
                startActivity(intent)
            }


        }
    }
}