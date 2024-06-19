package com.example.appmusickotlin.ui.home.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.ui.adapter.AlbumAdapter
import com.example.appmusickotlin.ui.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentPlaylistsfragmentBinding
import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.popup.DialogAddPlaylistFragment
import com.example.appmusickotlin.util.callBack.OnEditButtonClickListener
import com.example.appmusickotlin.util.callBack.OnItemClickListener
import com.example.appmusickotlin.util.callBack.PlaylistAddedListener
import com.example.appmusickotlin.db.viewmodel.MusicViewModel
import com.example.appmusickotlin.db.viewmodel.PlaylistViewModel


class PlayListsFragment : Fragment(), PlaylistAddedListener, OnItemClickListener,
    OnEditButtonClickListener {
    private lateinit var binding: FragmentPlaylistsfragmentBinding
    private var idPlayList: Long = 0
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var musicViewModel: MusicViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsfragmentBinding.inflate(inflater, container, false)

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        musicViewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)


        playlistViewModel.getPlaylist(User.userId!!)

        playlistViewModel.playlist?.observe(requireActivity(), Observer { playlist ->

            binding.grSwap.visibility = View.GONE
            binding.ibnSwap.visibility = View.GONE
            binding.ibnLinear.visibility = View.GONE
            binding.ibnGrid.visibility = View.GONE
            binding.rccAlbum.visibility = View.VISIBLE

            if (playlist.isNullOrEmpty()) {
                binding.group.visibility = View.VISIBLE
                binding.rccAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.GONE
            } else {

                val adapter = AlbumAdapter(playlist, this)
                binding.rccAlbum.layoutManager = LinearLayoutManager(this.context)
                binding.rccAlbum.adapter = adapter
                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.VISIBLE
                binding.rccMusicAlbum.visibility = View.GONE
            }


        })

        //hien lai man hinh album khi back lai
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
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


        binding.ibnGrid.setOnClickListener {
            binding.ibnGrid.visibility = View.GONE
            binding.ibnLinear.visibility = View.VISIBLE

            musicViewModel.getMusic(idPlayList)

            musicViewModel.music?.observe(requireActivity(), Observer { listMusic ->

                if (listMusic.isNullOrEmpty()) {
                    Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
                } else {
                    binding.rccAlbum.visibility = View.GONE
                    binding.rccMusicAlbum.visibility = View.GONE
                    binding.rccGridMusicAlbum.visibility = View.VISIBLE
                    binding.grSwap.visibility = View.GONE
                    binding.ibnSwap.visibility = View.VISIBLE
                    binding.ibnGrid.visibility = View.GONE
                    binding.ibnLinear.visibility = View.VISIBLE
                }

            })


        }


        binding.ibnLinear.setOnClickListener {
            binding.ibnLinear.visibility = View.GONE
            binding.ibnGrid.visibility = View.VISIBLE
            musicViewModel.getMusic(idPlayList)

            musicViewModel.music?.observe(requireActivity(), Observer { listMusic ->
                if (listMusic.isNullOrEmpty()) {
                    Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
                } else {
                    binding.rccAlbum.visibility = View.GONE
                    binding.rccGridMusicAlbum.visibility = View.GONE
                    binding.rccMusicAlbum.visibility = View.VISIBLE
                    binding.grSwap.visibility = View.GONE
                    binding.ibnSwap.visibility = View.VISIBLE
                    binding.ibnGrid.visibility = View.VISIBLE
                    binding.ibnLinear.visibility = View.GONE

                    //musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum)
                }
            })

        }
    }

    override fun onPlaylistAdded(album: DataListPlayList) {

        //User.albumsLst.add(album)
        playlistViewModel.getPlaylist(User.userId!!)

        binding.rccAlbum.visibility = View.VISIBLE

        playlistViewModel.playlist?.observe(requireActivity(), Observer { playlist ->

            val adapter = AlbumAdapter(playlist, this)
            binding.rccAlbum.layoutManager = LinearLayoutManager(this.context)
            binding.rccAlbum.adapter = adapter
            binding.group.visibility = View.GONE
            binding.rccAlbum.visibility = View.VISIBLE
        })

    }

    override fun onItemClick(position: Int, idPlayList: Long) {


        musicViewModel.getMusic(idPlayList)

        musicViewModel.music?.observe(requireActivity() , Observer { listMusic ->

            Log.d("MusicFragment", "Quan sát music LiveData và cập nhật UI")

            if (listMusic.isNullOrEmpty()) {
                //Toast.makeText(context, "chua co music ", Toast.LENGTH_SHORT).show()
            } else {
                val musicAdapter = MusicAdapter(
                    requireContext(),
                    listMusic, this, false, false
                )
                binding.rccMusicAlbum.layoutManager = LinearLayoutManager(this.context)
                binding.rccMusicAlbum.adapter = musicAdapter

                val musicGridAdapter = MusicAdapter(
                    requireContext(),
                    listMusic, this, false, true
                )
                binding.rccGridMusicAlbum.layoutManager = GridLayoutManager(this.context, 2)
                binding.rccGridMusicAlbum.adapter = musicGridAdapter

                binding.group.visibility = View.GONE
                binding.rccAlbum.visibility = View.GONE
                binding.rccMusicAlbum.visibility = View.VISIBLE
                binding.rccGridMusicAlbum.visibility = View.GONE
                binding.grSwap.visibility = View.GONE
                binding.ibnSwap.visibility = View.VISIBLE
                binding.ibnGrid.visibility = View.VISIBLE

                binding.ibnSwap.setOnClickListener {
                    binding.grSwap.visibility = View.VISIBLE
                    binding.ibnSwap.visibility = View.GONE
                    musicAdapter.setSwap(swap = true)
                    musicGridAdapter.enableSwipeAndDrag(binding.rccGridMusicAlbum, true)
                    musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum, true)
                }
                binding.grSwap.setOnClickListener {
                    binding.grSwap.visibility = View.GONE
                    binding.ibnSwap.visibility = View.VISIBLE
                    musicAdapter.setSwap(swap = false)
                    musicGridAdapter.enableSwipeAndDrag(binding.rccGridMusicAlbum, false)
                    musicAdapter.enableSwipeAndDrag(binding.rccMusicAlbum, false)
                }
            }
        })


        getPosition(idPlayList)
    }

    private fun getPosition(idPlayList: Long) {
        this.idPlayList = idPlayList
    }

    override fun onEditButtonClick(song: Song) {
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {

        musicViewModel.delete(song.id, idPlayList)
        Log.d("run","delete")
    }


}