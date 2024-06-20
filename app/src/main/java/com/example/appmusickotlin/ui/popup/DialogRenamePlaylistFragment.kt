package com.example.appmusickotlin.ui.popup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.DialogFragmentLayoutAddMusicBinding
import com.example.appmusickotlin.databinding.FragmentDialogAddLibraryBinding
import com.example.appmusickotlin.databinding.FragmentDialogRenamePlaylistBinding
import com.example.appmusickotlin.db.viewmodel.MusicViewModel
import com.example.appmusickotlin.db.viewmodel.PlaylistViewModel
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.adapter.AlbumAdapter
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.util.callBack.OnItemClickListener
import com.example.appmusickotlin.util.callBack.PlaylistAddedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.cancel

class DialogRenamePlaylistFragment(val playlistId : Long) : DialogFragment() {
    private lateinit var binding : FragmentDialogRenamePlaylistBinding
    private lateinit var playlistViewModel : PlaylistViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogRenamePlaylistBinding.inflate(layoutInflater)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvCreate.setOnClickListener{

            val name = binding.edtPlaylistTitle.text.toString()
            playlistViewModel.updateNamePlaylist(playlistId , name)
            dismiss()
        }
    }
}

