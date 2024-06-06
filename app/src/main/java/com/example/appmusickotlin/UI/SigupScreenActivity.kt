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
import com.example.appmusickotlin.validat.CheckInput
import com.example.appmusickotlin.controller.ControllerImpl
import com.example.appmusickotlin.model.User

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

        /**
         * btnButtonCheckValidity check validity
         * txtSignIn intent SigInScreenActivity
         */
        val btnButtonCheckValidity = findViewById<Button>(R.id.button)
        val txtSignIn = findViewById<TextView>(R.id.textView4)

        txtSignIn.setOnClickListener {
            val intent = Intent(this, SigInScreenActivity::class.java)
            startActivity(intent)
        }



        /**
         * btnButtonCheckValidity click
         */
        btnButtonCheckValidity.setOnClickListener {

            /**
             * edtEdtUsername, edtEmail, edtPhoneNumber, edtPassword, edtRePassword get value
             */
            val edtEdtUsername = findViewById<EditText>(R.id.editTextText1)
            val edtEmail = findViewById<EditText>(R.id.editTextText2)
            val edtPhoneNumber = findViewById<EditText>(R.id.editTextText3)
            val edtPassword = findViewById<EditText>(R.id.editTextText4)
            val edtRePassword = findViewById<EditText>(R.id.editTextText5)
            val controllerImpl = ControllerImpl()
            val user: User

            /**
             * user get value
             */

            user = User(
                edtEdtUsername.text.toString(),
                edtEmail.text.toString(),
                edtPhoneNumber.text.toString(),
                edtPassword.text.toString(),
                edtRePassword.text.toString()
            )
            /**
             * checkInput get value
             */

            val checkInput = CheckInput(
                user
            )

            /**
             * isValidusername,
             * isValidemail,
             * isValidphoneNumber,
             * isValidpassword,
             * isValidrePassword
             *
             * check validity
             */

            val isValidusername = checkInput.validUsername()
            val isValidemail = checkInput.validEmail()
            val isValidphoneNumber = checkInput.validPhoneNumber()
            val isValidpassword = checkInput.validPassword()
            val isValidrePassword = checkInput.validRePassword()

            /**
             * if isValidat unvalidty,
             */

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

            /**
             * if isValidat validty,
             */

            if (isValidusername == true && isValidemail == true && isValidphoneNumber == true && isValidpassword == true && isValidrePassword == true) {
                val intent = Intent(this, SigInScreenActivity::class.java)
                controllerImpl.SignUp(
                    edtEdtUsername.text.toString(),
                    edtEmail.text.toString(),
                    edtPhoneNumber.text.toString(),
                    edtPassword.text.toString(),
                    edtRePassword.text.toString()
                )

                /**
                 * startActivity SignInScreenActivity
                 */

                startActivity(intent)
            }


        }
    }
}