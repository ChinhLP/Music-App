package com.example.appmusickotlin.ui.authetication

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.example.appmusickotlin.controller.ControllerImpl
import com.example.appmusickotlin.databinding.ActivitySigInScreenBinding

class SigInScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigInScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySigInScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val controllerImpl = ControllerImpl()
            val text = controllerImpl.SignIn(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()


            if (text == "Đăng nhập thành công"){
                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        binding.imgShowPassword.setOnClickListener {
            if(binding.edtPassword.inputType ==  (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)){
                binding.edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.imgShowPassword.setImageResource(R.drawable.ic_eyeclose)
            }else{
                binding.edtPassword.inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.imgShowPassword.setImageResource(R.drawable.ic_eye)
            }
        }

        binding.txtSignup.setOnClickListener {
            val intent = Intent(this, SigupScreenActivity::class.java)
            startActivity(intent)
        }


    }

}