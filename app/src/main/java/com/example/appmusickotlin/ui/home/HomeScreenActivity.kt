package com.example.appmusickotlin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.home.Fragment.HomeFragment
import com.example.appmusickotlin.ui.home.Fragment.LibraryFragment
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.databinding.ActivityHomeScreenBinding
import com.example.appmusickotlin.db.viewmodel.PlaylistViewModel
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.retrofit2.viewmodel.MusicRemoteViewModel
//import com.example.appmusickotlin.model.saveUser
//import com.example.appmusickotlin.model.setMyUser
import com.example.appmusickotlin.ui.authetication.SigInScreenFragment
import com.example.appmusickotlin.ui.home.viewmodel.HomeViewModel
import com.example.appmusickotlin.util.formatDuration.formatDuration
import com.example.appmusickotlin.util.mediaPlay.MusicPlayer
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.launch
import java.util.Locale

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var musicPlayer: MusicPlayer
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicRemoteViewModel: MusicRemoteViewModel


    private var play = true
    private lateinit var music: Song


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("language_key", Locale.getDefault().language)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        playlistViewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        musicRemoteViewModel = ViewModelProvider(this).get(MusicRemoteViewModel::class.java)


        playlistViewModel.getPlaylist(User.userId!!)
        Log.d("ppp", "ccccc2")



        musicPlayer = MusicPlayer(this, binding.seekBar)


        homeViewModel.song.observe(this, Observer { song ->

            musicPlayer.stopAndRelease()

            binding.grPlayMusic.visibility =  View.VISIBLE

            musicPlayer.setDataSource(song.path!!)

            binding.txtNameMusic.text = song.name
            binding.txtDuration.text = song.duration!!.formatDuration()
        })

        binding.ibnPlay.setOnClickListener {
            if (play) {
                musicPlayer.play()
                play = false
            } else {
                musicPlayer.pause()
                play = true
            }
            changePlayMusic()

        }


        if (savedInstanceState == null) {
            showHomeFragment()
        }
        fragmentCheckManager()
    }


    private fun changePlayMusic() {
        if (play) {
            binding.ibnPlay.setImageResource(R.drawable.ic_play)
        } else {
            binding.ibnPlay.setImageResource(R.drawable.ic_pause)
        }
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
                    showHomeFragment()
                    true
                }

                R.id.btnLibrary -> {
                    showLibraryFragment()
                    true
                }

                R.id.btnPlaylist -> {
                    showPlayListsFragment()
                    true
                }

                else -> false
            }
        }


    }

    private fun showHomeFragment() {
        supportFragmentManager.commit {
            //setReorderingAllowed(false)
            replace(binding.fragmentContainer.id, HomeFragment())
        }
    }

    private fun showLibraryFragment() {
        musicRemoteViewModel.fetchAllMusic()
        supportFragmentManager.commit {
            //setReorderingAllowed(false)
            replace(binding.fragmentContainer.id, LibraryFragment())
        }
    }

    private fun showPlayListsFragment() {
        lifecycleScope.launch {
            supportFragmentManager.commit {
                replace(binding.fragmentContainer.id, PlayListsFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            // Nếu chỉ có một fragment trong ngăn xếp, kết thúc activity
            finish()
        }
    }
}