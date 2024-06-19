package com.example.appmusickotlin.ui.diaglogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.adapter.AlbumAdapter
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.example.appmusickotlin.databinding.FragmentDialogAddLibraryBinding
import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.example.appmusickotlin.util.callBack.OnItemClickListener
import com.example.appmusickotlin.db.viewmodel.MusicViewModel
import com.example.appmusickotlin.db.viewmodel.PlaylistViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class DialogAddLibraryFragment : DialogFragment(), OnItemClickListener {

    private lateinit var binding: FragmentDialogAddLibraryBinding
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicViewModel: MusicViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddLibraryBinding.inflate(layoutInflater)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        musicViewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)


        playlistViewModel.getPlaylist(User.userId!!)

        playlistViewModel.playlist?.observe(viewLifecycleOwner, Observer { playlist ->

            if (isAdded && viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                if (playlist.isNullOrEmpty()) {
                    binding.groupTextViews.visibility = View.VISIBLE
                    binding.rccAlbum.visibility = View.GONE
                } else {
                    binding.groupTextViews.visibility = View.GONE
                    binding.rccAlbum.visibility = View.VISIBLE
                    val adapter = AlbumAdapter(playlist, this)
                    binding.rccAlbum.layoutManager = LinearLayoutManager(requireContext())
                    binding.rccAlbum.adapter = adapter
                }
            }
        })

        binding.btnAddmusic.setOnClickListener {
            val newFragment = PlayListsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()

            val navigationBottom =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            navigationBottom.menu.findItem(R.id.btnPlaylist).setChecked(true)
            dismiss()
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hủy các tác vụ liên quan đến context nếu cần
        viewLifecycleOwner.lifecycleScope.cancel()
    }

    override fun onItemClick(position: Int, idPlayList : Long) {

        var song: Song? = arguments?.getSerializable("song") as? Song
        song!!.playlistId = idPlayList
        musicViewModel.insert(song)
        //musicViewModel.getMusic( song.id)

        dismiss()


    }

}

