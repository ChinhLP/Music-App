package com.example.appmusickotlin.ui.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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
import com.example.appmusickotlin.model.saveUser
import com.example.appmusickotlin.model.setMyUser
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
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicRemoteViewModel: MusicRemoteViewModel
    private lateinit var currentPositionObserver: Observer<Int>
    private lateinit var currentPlayObserver : Observer<Boolean>
    private lateinit var foregroundService: ForegroundService
    private lateinit var currentMediaObserver: Observer<Boolean>
    private lateinit var currentSongObserver : Observer<Song>


    private var play = true
//    private lateinit var songCurrent : Song

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("language_key", Locale.getDefault().language)
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as ForegroundService.LocalBinder
            foregroundService = binder.getService()

            // Quan sát currentPosition từ ForegroundService

            foregroundService.currentPosition.observe(this@HomeScreenActivity, currentPositionObserver)
            foregroundService.isPrepared.observe(this@HomeScreenActivity, currentPlayObserver)
            foregroundService.isMedia.observe(this@HomeScreenActivity, currentMediaObserver)
            foregroundService.song.observe(this@HomeScreenActivity, currentSongObserver)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // Xử lý khi mất kết nối với Service
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        playlistViewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        musicRemoteViewModel = ViewModelProvider(this).get(MusicRemoteViewModel::class.java)

        bindService(Intent(this, ForegroundService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
        // Liên kết SeekBar từ layout
        // Tạo observer để lắng nghe thay đổi của currentPosition


        playlistViewModel.getPlaylist(User.userId!!)


        currentMediaObserver = Observer { isMedia ->
            if(isMedia){
                binding.grPlayMusic.visibility = View.VISIBLE
                currentSongObserver = Observer { song ->
                    binding.txtNameMusic.text = song.name
                    binding.txtDuration.text = song.duration!!.formatDuration()
                    binding.seekBar.max = song.duration!!.toInt()
                    binding.vListener.setOnClickListener {
                        val intent = Intent(this,ListenMusicActivity::class.java)
                        val arg = Bundle()
                        arg.putSerializable("song", song)
                        intent.putExtras(arg)
                        startActivity(intent)
                    }

                }
                currentPositionObserver = Observer { newPosition ->
                    // Chỉ cập nhật giá trị của SeekBar khi không có sự kiện vuốt (tracking touch)
                    binding.seekBar.progress = newPosition
                }

                currentPlayObserver = Observer { isPlay ->
                    play = isPlay
                    changePlayMusic()
                }

            } else {
                binding.grPlayMusic.visibility = View.GONE
            }
        }

        currentSongObserver = Observer { song ->
            binding.txtNameMusic.text = song.name
            binding.txtDuration.text = song.duration!!.formatDuration()
            binding.seekBar.max = song.duration!!.toInt()
        }

        currentPlayObserver = Observer { isPlay ->
            play = isPlay
            changePlayMusic()
        }

        currentPositionObserver = Observer { newPosition ->

            // Chỉ cập nhật giá trị của SeekBar khi không có sự kiện vuốt (tracking touch)
            binding.seekBar.progress = newPosition
        }

        homeViewModel.listSong.observe(this, Observer { listSong ->
            val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                action = "ACTION_LOAD_LIST"
            }
            val args = Bundle()
            args.putSerializable("listSong",  ArrayList(listSong))
            serviceIntent.putExtras(args)
            startService(serviceIntent)

        })

        homeViewModel.song.observe(this, Observer { song ->
            Log.d("dddd", song.toString())
            val serviceIntents = Intent(this, ForegroundService::class.java).apply {
                action = "ACTION_RESUME"
            }
            val arg = Bundle()
            arg.putSerializable("song",  song)
            serviceIntents.putExtras(arg)
            startService(serviceIntents)



        })


        binding.vListener.setOnClickListener {
            val intent = Intent(this,ListenMusicActivity::class.java)

            startActivity(intent)
        }
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
                    action = "ACTION_PAUSE"
                }
                Log.d("eee" , "pause")
                startService(serviceIntent)
            } else {
                val serviceIntent = Intent(this, ForegroundService::class.java).apply {
                    action = "ACTION_PLAY"
                }
                Log.d("eee" , "play")

                startService(serviceIntent)
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
            binding.ibnPlay.setImageResource(R.drawable.ic_pause)
        } else {
            binding.ibnPlay.setImageResource(R.drawable.ic_play)
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
        if (::foregroundService.isInitialized) {
            foregroundService.currentPosition.removeObserver(currentPositionObserver)
            unbindService(serviceConnection)
        }
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            // Nếu chỉ có một fragment trong ngăn xếp, kết thúc activity
            finish()
        }
    }

}