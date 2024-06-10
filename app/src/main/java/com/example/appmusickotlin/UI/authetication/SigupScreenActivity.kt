package com.example.appmusickotlin.UI.authetication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.common.validat.CheckInput
import com.example.appmusickotlin.controller.ControllerImpl
import com.example.appmusickotlin.databinding.ActivitySigupScreenBinding
import com.example.appmusickotlin.model.User

class SigupScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigupScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySigupScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        if (savedInstanceState != null) {
//            // Khôi phục dữ liệu từ Bundle nếu có
//            val restoredData = savedInstanceState.getString("key")
//            // Tiếp tục xử lý dữ liệu
//            if (restoredData != null) {
//                binding.txtNextSignin.setText(restoredData)
//            }
//        }

        /**
         * btnButtonCheckValidity check validity
         * txtSignIn intent SigInScreenActivity
         */


        binding.txtNextSignin.setOnClickListener {
            finish()
        }



        /**
         * btnButtonCheckValidity click
         */
        binding.btnSignup.setOnClickListener {

            /**
             * edtEdtUsername, edtEmail, edtPhoneNumber, edtPassword, edtRePassword get value
             */

            val controllerImpl = ControllerImpl()
            val user: User

            /**
             * user get value
             */

            user = User(
                binding.edtUsername.text.toString(),
                binding.edtEmailSignup.text.toString(),
                binding.edtPhone.text.toString(),
                binding.edtPasswordSignup.text.toString(),
                binding.edtRepassword.text.toString()
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
                    binding.edtUsername.text.toString(),
                    binding.edtEmailSignup.text.toString(),
                    binding.edtPhone.text.toString(),
                    binding.edtPasswordSignup.text.toString(),
                    binding.edtRepassword.text.toString()
                )

                /**
                 * startActivity SignInScreenActivity
                 */

                startActivity(intent)
            }


        }
    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        // Lưu dữ liệu vào Bundle
//        val textToSave = binding.edtUsername.text.toString()
//        outState.putString("key", textToSave)
//        super.onSaveInstanceState(outState)
//    }
}