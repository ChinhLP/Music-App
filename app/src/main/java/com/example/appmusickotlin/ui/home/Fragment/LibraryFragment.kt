package com.example.appmusickotlin.ui.home.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.example.appmusickotlin.ui.popup.DialogPermissionsStorageFragment


class LibraryFragment : Fragment(), OnEditButtonClickListener, OnMusicClickListener {

    private lateinit var binding: FragmentLibraryfragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var musicViewModel: MusicViewModel
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicRemoteViewModel: MusicRemoteViewModel

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



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkStoragePermission()) {
            // Quyền đã được cấp, bạn có thể truy cập vào bộ nhớ lưu trữ

        } else {
            val dialog = DialogPermissionsStorageFragment()
            dialog.show(childFragmentManager,"dialog permissions storage")
        }

        val listMusic = MusicLoader(requireContext()).getAllMusic()

        val adapter = MusicAdapter(this.context, listMusic, this, true, false)

        adapter.setOnMusicClickListener(this)
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
                        val adapterRemote =
                            MusicAdapter(this.context, musicRemoteList!!, this, true, false)
                        adapterRemote.setOnMusicClickListener(this)
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
        args.putSerializable("song", song) // Đặt đối tượng Song vào Bundle
        // Đặt Bundle vào DialogFragment
        addDialog.arguments = args

        addDialog.show(childFragmentManager, "Add Dialog")
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {
    }

    override fun onItemClick(song: Song, listPlays: MutableList<Song>) {
        viewModel.playSong(song, listPlays)
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}