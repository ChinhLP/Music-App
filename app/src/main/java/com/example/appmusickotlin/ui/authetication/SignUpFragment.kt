package com.example.appmusickotlin.ui.authetication

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.FragmentSigupScreenBinding
import com.example.appmusickotlin.data.local.db.database.AppDatabase
import com.example.appmusickotlin.data.local.db.entity.UserEntity
import com.example.appmusickotlin.ui.authetication.viewmodel.AuthViewModel
import com.example.appmusickotlin.util.validate.Validate
import com.example.appmusickotlin.data.local.db.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSigupScreenBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigupScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)


        binding.imgShowPassword.setOnClickListener {
            togglePasswordVisibility(binding.edtPasswordSignup, binding.imgShowPassword)
        }

        binding.imgShowRePassword.setOnClickListener {
            togglePasswordVisibility(binding.edtRepassword, binding.imgShowRePassword)
        }

        binding.btnSignup.setOnClickListener {
            val checkInput = Validate(
                binding.edtUsername.text.toString(),
                binding.edtEmailSignup.text.toString(),
                binding.edtPasswordSignup.text.toString(),
                binding.edtRepassword.text.toString()
            )

            val isValidUsername = checkInput.validUsername()
            val isValidEmail = checkInput.validEmail()
            val isValidPassword = checkInput.validPassword()
            val isValidRePassword = checkInput.validRePassword()

            if (!isValidUsername) {
                binding.txtErrorUsername.visibility = View.VISIBLE
                binding.edtUsername.setText("")
            } else {
                binding.txtErrorUsername.visibility = View.INVISIBLE
            }
            if (!isValidEmail) {
                binding.txtErrorEmail.visibility = View.VISIBLE
                binding.edtEmailSignup.setText("")
            } else {
                binding.txtErrorEmail.visibility = View.INVISIBLE
            }
            if (!isValidPassword) {
                binding.txtErrorPassword.visibility = View.VISIBLE
                binding.edtPasswordSignup.setText("")
            } else {
                binding.txtErrorPassword.visibility = View.INVISIBLE
            }
            if (!isValidRePassword) {
                binding.txtErrorRepassword.visibility = View.VISIBLE
                binding.edtRepassword.setText("")
            } else {
                binding.txtErrorRepassword.visibility = View.INVISIBLE
            }

            if (isValidUsername && isValidEmail && isValidPassword && isValidRePassword) {
                val username = binding.edtUsername.text.toString()
                val email = binding.edtEmailSignup.text.toString()
                val password = binding.edtPasswordSignup.text.toString()

                val user = UserEntity(username = username , email = email, password = password )
                userViewModel.insert(user)


                authViewModel.navigateToSignIn()

            }
        }

        setupFocusListeners()
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        if (editText.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.ic_eyeclose)
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.ic_eye)
        }
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