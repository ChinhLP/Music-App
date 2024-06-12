package com.example.appmusickotlin.ui.authetication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.appmusickotlin.R
import com.example.appmusickotlin.common.validat.CheckInput
import com.example.appmusickotlin.controller.ControllerImpl
import com.example.appmusickotlin.databinding.ActivitySigupScreenBinding
import com.example.appmusickotlin.model.User

class SigupScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigupScreenBinding

    @UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySigupScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgShowPassword.setOnClickListener{
            if(binding.edtPasswordSignup.inputType == 129) {
                binding.edtPasswordSignup.inputType = 143
                binding.imgShowPassword.setImageResource(R.drawable.ic_eyeclose)
            }else{
                binding.edtPasswordSignup.inputType = 129
                binding.imgShowPassword.setImageResource(R.drawable.ic_eye)
            }
        }

        binding.imgShowRePassword.setOnClickListener{
            if(binding.edtRepassword.inputType == 129) {
                binding.edtRepassword.inputType = 143
                binding.imgShowRePassword.setImageResource(R.drawable.ic_eyeclose)
            }else{
                binding.edtRepassword.inputType = 129
                binding.imgShowRePassword.setImageResource(R.drawable.ic_eye)
            }
        }


        binding.btnSignup.setOnClickListener {
            val controllerImpl = ControllerImpl()
            val user: User

            user = User(
                binding.edtUsername.text.toString(),
                binding.edtEmailSignup.text.toString(),
                binding.edtPasswordSignup.text.toString(),
                binding.edtRepassword.text.toString()
            )

            val checkInput = CheckInput(
                user
            )

            val isValidusername = checkInput.validUsername()
            val isValidemail = checkInput.validEmail()
            val isValidpassword = checkInput.validPassword()
            val isValidrePassword = checkInput.validRePassword()

            if (isValidusername == false) {
                binding.txtErrorUsername.visibility = View.VISIBLE
                binding.edtUsername.setText("")
            }
            if (isValidemail == false) {
                binding.txtErrorEmail.visibility = View.VISIBLE
                binding.edtEmailSignup.setText("")
            }
            if (isValidpassword == false) {
                binding.txtErrorPassword.visibility = View.VISIBLE
                binding.edtPasswordSignup.setText("")
            }
            if (isValidrePassword == false) {
                binding.txtErrorRepassword.visibility = View.VISIBLE
                binding.edtRepassword.setText("")
                Log.d("TAG", "onCreate: ${binding.edtRepassword.text.toString()} , ${binding.edtPasswordSignup.text.toString()}")
            }

            if (isValidusername == true && isValidemail == true  && isValidpassword == true && isValidrePassword == true) {
                val intent = Intent(this, SigInScreenActivity::class.java)

                controllerImpl.SignUp(
                    binding.edtUsername.text.toString(),
                    binding.edtEmailSignup.text.toString(),
                    binding.edtPasswordSignup.text.toString(),
                    binding.edtRepassword.text.toString()

                )
                startActivity(intent)
            }
        }
        setupFocusListeners()



    }

    private fun setupFocusListeners() {
        binding.edtUsername.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.txtErrorUsername.visibility = View.INVISIBLE
            }
        }

        binding.edtEmailSignup.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.txtErrorEmail.visibility = View.INVISIBLE
            }
        }

        binding.edtPasswordSignup.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.txtErrorPassword.visibility = View.INVISIBLE
            }
        }

        binding.edtRepassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.txtErrorRepassword.visibility = View.INVISIBLE
            }
        }
    }
}