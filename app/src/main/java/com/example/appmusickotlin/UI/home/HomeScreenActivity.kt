package com.example.appmusickotlin.UI.home

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmusickotlin.R
import com.example.appmusickotlin.UI.home.Fragment.HomeFragment
import com.example.appmusickotlin.UI.home.Fragment.LibraryFragment
import com.example.appmusickotlin.UI.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.broadcastReceivers.BluetoothReceiver
import com.example.appmusickotlin.databinding.ActivityHomeScreenBinding
import java.util.Locale

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var bluetoothReceiver: BluetoothReceiver


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("language_key", Locale.getDefault().language)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bluetoothReceiver()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        fragmentCheckManager()



    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
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

    private fun bluetoothReceiver() {

        bluetoothReceiver = BluetoothReceiver()

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }
        registerReceiver(bluetoothReceiver, intentFilter)
    }

    private fun getAllMusic(): List<Uri> {


        val musicUriList = mutableListOf<Uri>()


        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
        )

        val musicCursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            null,
            null,
            null
        )

        musicCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val data = cursor.getString(dataColumn)

                // In thông tin âm nhạc ra màn hình
                Log.d("Music", "Id: $id, Title: $title, Artist: $artist, Data: $data")
            }
        }

        return musicUriList
    }


}