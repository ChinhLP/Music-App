package com.example.appmusickotlin.ui.authetication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.ActivityAuthBinding
import com.example.appmusickotlin.model.MyUser
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.model.saveUser
import com.example.appmusickotlin.model.setMyUser
import com.example.appmusickotlin.ui.authetication.viewmodel.AuthViewModel

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        enableEdgeToEdge()
        if (savedInstanceState == null) {
            showSignInFragment()
        }

        // Observe the currentFragment LiveData
        viewModel.currentFragment.observe(this,Observer {
                fragment ->
            when (fragment) {
                "SignIn" -> showSignInFragment()
                "SignUp" -> showSignUpFragment()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        val user = setMyUser()
        saveUser(user)
    }


    private fun showSignInFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.fragmentContainer.id, SigInScreenFragment())
        }
    }

    private fun showSignUpFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.fragmentContainer.id, SignUpFragment())
        }
    }
}