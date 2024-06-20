package com.example.appmusickotlin.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.home.Fragment.HomeFragment
import com.example.appmusickotlin.ui.home.Fragment.LibraryFragment
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.databinding.ActivityHomeScreenBinding
import com.example.appmusickotlin.data.local.db.viewmodel.PlaylistViewModel
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.MusicRemoteViewModel
import com.example.appmusickotlin.service.ForegroundService
//import com.example.appmusickotlin.model.saveUser
//import com.example.appmusickotlin.model.setMyUser
import com.example.appmusickotlin.ui.home.viewmodel.HomeViewModel
import com.example.appmusickotlin.util.formatDuration.formatDuration
import com.example.appmusickotlin.service.MusicPlayer
import com.example.appmusickotlin.ui.ListenMusic.ListenMusicActivity
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

        homeViewModel.song.observe(this, Observer { song ->
            val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                action = "ACTION_RESUME"
            }
            serviceIntent.putExtra("songPlay", song.path)
            serviceIntent.putExtra("nameSong",song.name)
            serviceIntent.putExtra("durationSong", song.duration!!.formatDuration())
            startService(serviceIntent)

            binding.grPlayMusic.visibility =  View.VISIBLE
            play = false
            changePlayMusic()


            binding.txtNameMusic.text = song.name

            binding.txtDuration.text = song.duration!!.formatDuration()
            binding.vListener.setOnClickListener {
                val intent = Intent(this,ListenMusicActivity::class.java)
                val args = Bundle()
                args.putSerializable("song", song)
                intent.putExtras(args)

                startActivity(intent)
            }

        })

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })

        binding.ibnCloseService.setOnClickListener {
            val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                action = "ACTION_CLOSE"
            }
            startService(serviceIntent)
            binding.grPlayMusic.visibility =  View.GONE
        }



        binding.ibnPlay.setOnClickListener {
            if (play) {
                val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                    action = "ACTION_PLAY"
                }
                startService(serviceIntent)
                play = false
            } else {
                val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                    action = "ACTION_PAUSE"
                }
                startService(serviceIntent)

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