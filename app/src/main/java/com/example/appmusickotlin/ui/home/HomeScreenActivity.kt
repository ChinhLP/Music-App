package com.example.appmusickotlin.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.home.Fragment.HomeFragment
import com.example.appmusickotlin.ui.home.Fragment.LibraryFragment
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.databinding.ActivityHomeScreenBinding
import java.util.Locale

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("language_key", Locale.getDefault().language)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentCheckManager()
    }

    private fun fragmentCheckManager() {


        val fragmentManager = supportFragmentManager
        val fragmentCount = fragmentManager.backStackEntryCount

        if (fragmentCount == 0) {
            val fragment = HomeFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack("HomeFragment")
            transaction.commit()
        }


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    // Nếu không có fragment nào trong stack, thực hiện chuyển đổi fragment
                    if (supportFragmentManager.backStackEntryCount == 0) {
                        // Nếu không có fragment nào trong stack, thực hiện chuyển đổi fragment
                        val fragment = HomeFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container, fragment)
                        transaction.addToBackStack("BlankFragment2")
                        transaction.commit()
                        // Trả về true sau khi giao dịch được thêm vào thành công
                    }

                    val fragment = HomeFragment()
                    val transaction = supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.fragment_container, fragment)

                    transaction.addToBackStack("BlankFragment2")

                    transaction.commit()
                    true
                }

                R.id.btnLibrary -> {

                    val fragment = LibraryFragment()


                    // Bắt đầu một FragmentTransaction
                    val transaction = supportFragmentManager.beginTransaction()

                    // Thay đổi Fragment hiện tại (Fragment2) bằng Fragment3
                    transaction.replace(R.id.fragment_container, fragment)

                    // Thêm transaction vào Back Stack nếu bạn muốn cho phép quay lại Fragment trước đó bằng nút back
                    transaction.addToBackStack("BlankFragment3")

                    // Commit FragmentTransaction
                    transaction.commit()
                    // Xử lý khi nút Library được chọn
                    true
                }

                R.id.btnPlaylist -> {
                    val fragment = PlayListsFragment()

                    val transaction = supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.fragment_container, fragment)

                    transaction.addToBackStack("BlankFragment1")

                    transaction.commit()
                    // Xử lý khi nút Playlist được chọn
                    true
                }

                else -> false
            }
        }


    }

}