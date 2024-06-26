package com.example.appmusickotlin.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.bumptech.glide.Glide
import com.example.appmusickotlin.R
import com.example.appmusickotlin.data.local.db.entity.UserEntity
import com.example.appmusickotlin.data.local.db.viewmodel.UserViewModel
import com.example.appmusickotlin.databinding.ActivityProfileBinding
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.model.logout
import com.example.appmusickotlin.ui.authetication.AuthActivity
import com.example.appmusickotlin.ui.home.HomeScreenActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var userViewModel: UserViewModel
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)
        binding.edtUsername.setText(User.username)
        binding.edtEmailSignup.setText(User.email)
        binding.imbBack.setOnClickListener {
            val intent = Intent(this,HomeScreenActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.imvCamera.setOnClickListener {
            openGallery()
        }
        binding.imbOk.setOnClickListener {
            User.username = binding.edtUsername.text.toString()
            User.email = binding.edtEmailSignup.text.toString()
            val user = UserEntity(User.userId!!, User.username!!, User.email!!, User.password!!)
            userViewModel.insert(user)
            binding.edtUsername.setText(User.username)
            binding.edtEmailSignup.setText(User.email)
            finish()
        }
        binding.grLogOut.setOnClickListener {
            logout(this)
        }

    }

    @UnstableApi
    override fun onResume() {
        super.onResume()
        if(User.imageAvatar != ""){
            Glide.with(this)
                .load(User.imageAvatar)
                .placeholder(R.drawable.profile) // Hình ảnh hiển thị trong khi chờ tải
                .error(R.drawable.profile) // Hình ảnh hiển thị khi tải thất bại
                .into(binding.imvAvatar)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            binding.imvAvatar.setImageURI(selectedImageUri)
            // Bạn có thể lưu URI của ảnh vào SharedPreferences hoặc bất kỳ nơi nào khác để sử dụng sau
            User.imageAvatar = selectedImageUri.toString()
            val user = UserEntity(User.userId!!, User.username!!, User.email!!, User.password!!,
                selectedImageUri.toString()
            )
            userViewModel.insert(user)

        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
}