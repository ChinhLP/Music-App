package com.example.appmusickotlin.ui.home.Fragment

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.R
import com.example.appmusickotlin.data.local.contendProvider.MusicLoader
import com.example.appmusickotlin.ui.diaglogs.DialogAddLibraryFragment
import com.example.appmusickotlin.ui.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentLibraryfragmentBinding
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.ui.home.viewmodel.HomeViewModel
import com.example.appmusickotlin.util.callBack.OnEditButtonClickListener
import com.example.appmusickotlin.util.callBack.OnMusicClickListener
import com.example.appmusickotlin.data.local.db.viewmodel.MusicViewModel
import com.example.appmusickotlin.data.local.db.viewmodel.PlaylistViewModel
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.MusicRemoteViewModel
import com.example.appmusickotlin.model.currentSong
import com.example.appmusickotlin.service.ForegroundService
import com.example.appmusickotlin.ui.popup.DialogPermissionsStorageFragment


class LibraryFragment : Fragment(), OnEditButtonClickListener, OnMusicClickListener {

    private lateinit var binding: FragmentLibraryfragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var musicViewModel: MusicViewModel
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicRemoteViewModel: MusicRemoteViewModel
    private lateinit var foregroundService: ForegroundService
    private lateinit var currentSongObserver : Observer<Song>
    private lateinit var currentMediaObserver: Observer<Boolean>


    private var adapter : MusicAdapter? = null
    private var adapterRemote : MusicAdapter? = null



    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as ForegroundService.LocalBinder
            foregroundService = binder.getService()

            // Quan sát currentPosition từ ForegroundService
            if (isAdded) {

                foregroundService.song.observe(requireActivity(), currentSongObserver)
                foregroundService.isPrepared.observe(requireActivity(), currentMediaObserver)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // Xử lý khi mất kết nối với Service
        }
    }

    override fun onResume() {
        super.onResume()
        if(currentSong.currentSong != null){
            adapter!!.setCurrent(currentSong.currentSong!!)

            adapterRemote!!.setCurrent(currentSong.currentSong!!)
        }

        currentSongObserver = Observer { music ->
            adapter?.setCurrent(music)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryfragmentBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        musicViewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        musicRemoteViewModel =
            ViewModelProvider(requireActivity()).get(MusicRemoteViewModel::class.java)

        val intent = Intent(requireContext(),ForegroundService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkStoragePermission()) {
            // Quyền đã được cấp, bạn có thể truy cập vào bộ nhớ lưu trữ

        } else {
            val dialog = DialogPermissionsStorageFragment()
            dialog.show(childFragmentManager, "dialog permissions storage")
        }

        val listMusic = MusicLoader(requireContext()).getAllMusic()

         adapter = MusicAdapter(this.context, listMusic, this, true, false)

        if(currentSong.currentSong != null){
            adapter!!.setCurrent(currentSong.currentSong!!)

              adapterRemote!!.setCurrent(currentSong.currentSong!!)
    }

    currentSongObserver = Observer { music ->
            adapter!!.setCurrent(music)
            currentSong.currentSong = music
        }
        currentMediaObserver = Observer { isPlay ->
            adapter!!.setIsPlay(isPlay)
            currentSong.isPlay = isPlay

        }

        currentSongObserver = Observer { music ->
            adapterRemote!!.setCurrent(music)
            currentSong.currentSong = music

        }

        adapter!!.setOnMusicClickListener(this)
        binding.recyclerView.adapter = adapter

        binding.btnLeft.setOnClickListener {
            binding.btnLeft.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.homeFocus
                )
            )
            binding.btnRight.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.DialogBackground
                )
            )
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerRemoteView.visibility = View.GONE
            binding.grNoInternet.visibility = View.GONE
            binding.vAnimation.visibility = View.GONE
        }



        binding.btnRight.setOnClickListener {
            musicRemoteViewModel.fetchAllMusic()

            musicRemoteViewModel.musicList.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    is State.Success -> {
                        val musicRemoteList = state.data
                        adapterRemote =
                            MusicAdapter(this.context, musicRemoteList!!, this, true, false)
                        adapterRemote!!.setOnMusicClickListener(this)
                        binding.recyclerRemoteView.adapter = adapterRemote
                        binding.recyclerRemoteView.visibility = View.VISIBLE
                        binding.grNoInternet.visibility = View.GONE
                        binding.vAnimation.visibility = View.GONE
                    }

                    is State.Error -> {
                        binding.grNoInternet.visibility = View.VISIBLE
                        binding.recyclerRemoteView.visibility = View.GONE
                        binding.vAnimation.visibility = View.GONE
                    }

                    is State.Loading -> {
                        binding.vAnimation.visibility = View.VISIBLE
                        binding.grNoInternet.visibility = View.GONE
                        binding.recyclerRemoteView.visibility = View.GONE
                    }

                    else -> {}
                }
            })

            binding.btnLeft.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.DialogBackground
                )
            )
            binding.btnRight.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.homeFocus
                )
            )

            binding.recyclerView.visibility = View.GONE
        }

        // không cho back lại
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    override fun onEditButtonClick(song: Song) {

        val addDialog = DialogAddLibraryFragment()
        val args = Bundle()
        val songee = Song(
            name = song.name,
            duration = song.duration,
            kind = song.kind,
            albumId = song.albumId,
            artist = song.artist,
            path = song.path,
            playlistId = song.playlistId
        )
        args.putSerializable("song", songee) // Đặt đối tượng Song vào Bundle
        // Đặt Bundle vào DialogFragment
        addDialog.arguments = args

        addDialog.show(childFragmentManager, "Add Dialog")
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {
    }

    override fun onItemClick(song: Song, listPlays: MutableList<Song>) {
        viewModel.playSong(song, listPlays)
    }

    fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}