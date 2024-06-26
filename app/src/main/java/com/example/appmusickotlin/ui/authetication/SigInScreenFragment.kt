package com.example.appmusickotlin.ui.authetication

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.room.InvalidationTracker
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.FragmentSigInScreenBinding
import com.example.appmusickotlin.data.local.db.database.AppDatabase
import com.example.appmusickotlin.data.local.db.viewmodel.MusicViewModel
import com.example.appmusickotlin.data.local.db.viewmodel.PlaylistViewModel
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.model.User.username
import com.example.appmusickotlin.ui.authetication.viewmodel.AuthViewModel
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.example.appmusickotlin.data.local.db.viewmodel.UserViewModel
import com.example.appmusickotlin.model.saveUser
import com.example.appmusickotlin.model.setMyUser
import com.example.appmusickotlin.ui.popup.DialogPermissionsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SigInScreenFragment : Fragment() {
    private var binding: FragmentSigInScreenBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicViewModel: MusicViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigInScreenBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        musicViewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)


        val dialog = DialogPermissionsFragment()
        dialog.show(childFragmentManager, "MyDialogFragment")


        authViewModel.user.observe(requireActivity(), Observer { user ->
            binding?.edtEmail?.setText(user.username)
            binding?.edtPassword?.setText(user.password)
        })

        binding?.btnLogin?.setOnClickListener {

            val userName = binding!!.edtEmail.text.toString()
            val password = binding!!.edtPassword.text.toString()

            // Query dữ liệu từ database

            userViewModel.getUser(userName, password)

            userViewModel.user.observe(requireActivity(),Observer{ user ->
                lifecycleScope.launchWhenResumed {
                    if (user != null && isAdded) {
                        User.userId = user.id
                        User.username = user.username
                        User.password = user.password
                        User.email = user.email
                        User.imageAvatar = user.imageAvatar

                        val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
                        startActivity(intent)

                        val myUser = setMyUser()
                        saveUser(myUser)

                        requireActivity().finish()
                    } else {

                        Toast.makeText(requireActivity(), "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }


            })
            // Xử lý dữ liệu user ở đây


        }

        showPassword()
        backSignUp()
    }

    private fun backSignUp() {
        binding?.txtSignup?.setOnClickListener {
            authViewModel.navigateToSignUp()
        }
    }

    private fun showPassword() {
        binding!!.imgShowPassword.setOnClickListener {
            if (binding?.edtPassword!!.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding!!.edtPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding!!.imgShowPassword.setImageResource(R.drawable.ic_eyeclose)
            } else {
                binding!!.edtPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding!!.imgShowPassword.setImageResource(R.drawable.ic_eye)
            }
            // Move the cursor to the end of the text
            binding!!.edtPassword.setSelection(binding!!.edtPassword.text.length)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}