package com.example.appmusickotlin.ui.home.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.adapter.AlbumAdapter
import com.example.appmusickotlin.adapter.MusicAdapter
import com.example.appmusickotlin.adapter.OnEditButtonClickListener
import com.example.appmusickotlin.adapter.OnItemClickListener

import com.example.appmusickotlin.databinding.FragmentPlaylistsfragmentBinding
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.ListAlbums
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.ui.dialogs.DialogAddPlaylistFragment


interface PlaylistAddedListener {
    fun onPlaylistAdded(album: DataListPlayList)
}

class PlayListsFragment : Fragment(), PlaylistAddedListener , OnItemClickListener,
    OnEditButtonClickListener {
    private lateinit var binding: FragmentPlaylistsfragmentBinding
    private var position : Int = 0
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsfragmentBinding.inflate(inflater, container, false)

        val album = ListAlbums.albumList

        binding.grSwap.visibility = View.GONE
        binding.ibnSwap.visibility = View.GONE
        binding.ibnLinear.visibility = View.GONE
        binding.ibnGrid.visibility = View.GONE

        if(album.isNullOrEmpty()){
            binding.group.visibility = View.VISIBLE
            binding.rccAlbum.visibility = View.GONE
            binding.rccMusicAlbum.visibility = View.GONE

        }
        else {
            val adapter = AlbumAdapter(ListAlbums.albumList!!,this)
            binding.rccAlbum.layoutManager = LinearLayoutManager(this.context)
            binding.rccAlbum.adapter = adapter
            binding.group.visibility = View.GONE
            binding.rccAlbum.visibility = View.VISIBLE
            binding.rccMusicAlbum.visibility = View.GONE
        }


        //hien lai man hinh album khi back lai
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object:
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.VISIBLE
                binding.rccMusicAlbum.visibility = View.GONE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.GONE
                binding.ibnLinear.visibility = View.GONE
                binding.ibnGrid.visibility = View.GONE
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPlaylist.setOnClickListener {
            val dialogAddPlaylist = DialogAddPlaylistFragment()
            dialogAddPlaylist.setPlaylistAddedListener(this) // phải thêm hàm này vào
            dialogAddPlaylist.show(childFragmentManager, "Add Playlist")
        }

        binding.ibnSwap.setOnClickListener {
            binding.grSwap.visibility = View.VISIBLE
            binding.ibnSwap.visibility = View.GONE
        }

        binding.ibnGrid.setOnClickListener{
            binding.ibnGrid.visibility = View.GONE
            binding.ibnLinear.visibility = View.VISIBLE
            val selectedAlbum = ListAlbums.albumList?.get(position)
            val musicList = selectedAlbum?.listMusic

            if(musicList.isNullOrEmpty()){
                Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
            } else {
                val musicAdapter = MusicAdapter(this.context, musicList,this,false,true,true)
                binding.rccMusicAlbum.layoutManager = GridLayoutManager(this.context,2)
                binding.rccMusicAlbum.adapter = musicAdapter
                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.VISIBLE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.VISIBLE
                binding.ibnGrid.visibility = View.GONE
                binding.ibnLinear.visibility = View.VISIBLE
                musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum)
            }

        }
        binding.ibnLinear.setOnClickListener{
            binding.ibnLinear.visibility = View.GONE
            binding.ibnGrid.visibility = View.VISIBLE
            val selectedAlbum = ListAlbums.albumList?.get(position)
            val musicList = selectedAlbum?.listMusic

            if(musicList.isNullOrEmpty()){
                Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
            } else {
                val musicAdapter = MusicAdapter(this.context, musicList,this,false,true,false)
                binding.rccMusicAlbum.layoutManager = LinearLayoutManager(this.context)
                binding.rccMusicAlbum.adapter = musicAdapter
                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.VISIBLE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.VISIBLE
                binding.ibnGrid.visibility = View.VISIBLE
                binding.ibnLinear.visibility = View.GONE
                musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum)
            }
        }
    }
    override fun onPlaylistAdded(album: DataListPlayList) {

        if (ListAlbums.albumList == null) {
            ListAlbums.albumList = mutableListOf()
        }
        ListAlbums.albumList?.add(album)

        val adapter = AlbumAdapter(ListAlbums.albumList!!,this)
        binding.rccAlbum.layoutManager = LinearLayoutManager(this.context)
        binding.rccAlbum.adapter = adapter
        binding.group.visibility = View.GONE
        binding.rccAlbum.visibility = View.VISIBLE


    }

    override fun onItemClick(position: Int) {

        val selectedAlbum = ListAlbums.albumList?.get(position)
        val musicList = selectedAlbum?.listMusic

        if(musicList.isNullOrEmpty()){
            Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
        } else {
            val musicAdapter = MusicAdapter(this.context, musicList,this,false,true,false)
            binding.rccMusicAlbum.layoutManager = LinearLayoutManager(this.context)
            binding.rccMusicAlbum.adapter = musicAdapter
            binding.group.visibility = View.GONE
            binding.rccAlbum.visibility = View.GONE
            binding.rccMusicAlbum.visibility = View.VISIBLE
            binding.grSwap.visibility = View.GONE
            binding.ibnSwap.visibility = View.VISIBLE
            binding.ibnGrid.visibility = View.VISIBLE
        }
        getPosition(position)
          }

    private fun getPosition(position: Int): Int {
        this.position = position
        return  position
    }

    override fun onEditButtonClick(song: Song) {
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {
        // code chat GPT
        val selectedAlbum = ListAlbums.albumList?.find { it.listMusic?.contains(song) == true }
        selectedAlbum?.listMusic?.remove(song)
        binding.rccMusicAlbum.adapter?.notifyItemRemoved(position)
    }

}