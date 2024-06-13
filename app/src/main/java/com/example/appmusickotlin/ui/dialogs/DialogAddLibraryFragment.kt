package com.example.appmusickotlin.ui.diaglogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.R
import com.example.appmusickotlin.adapter.AlbumAdapter
import com.example.appmusickotlin.adapter.OnItemClickListener
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.example.appmusickotlin.databinding.FragmentDialogAddLibraryBinding
import com.example.appmusickotlin.model.ListAlbums
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class DialogAddLibraryFragment : DialogFragment(), OnItemClickListener {

    private lateinit var binding: FragmentDialogAddLibraryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddLibraryBinding.inflate(layoutInflater)

        val album = ListAlbums.albumList
        if(album.isNullOrEmpty()){
            binding.groupTextViews.visibility = View.VISIBLE
            binding.rccAlbum.visibility = View.GONE
        } else {
            binding.groupTextViews.visibility = View.GONE
            binding.rccAlbum.visibility = View.VISIBLE
            val adapter = AlbumAdapter(ListAlbums.albumList!!,this)
            binding.rccAlbum.layoutManager = LinearLayoutManager(requireContext())
            binding.rccAlbum.adapter = adapter
        }


        binding.btnAddmusic.setOnClickListener {
            val newFragment = PlayListsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()

            val navigationBottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            navigationBottom.menu.findItem(R.id.btnPlaylist).setChecked(true)
            dismiss()
        }

        return  binding.root

    }

    override fun onItemClick(position: Int) {
        val song: Song? = arguments?.getSerializable("song") as? Song
        val selectedAlbum = ListAlbums.albumList?.get(position)
       // Kiểm tra nếu selectedAlbum không null và selectedAlbum.listMusic là null
        if (selectedAlbum != null) {
            if (selectedAlbum.listMusic == null) {
                selectedAlbum.listMusic = mutableListOf() // Khởi tạo listMusic nếu null
            }
            selectedAlbum.listMusic?.add(song!!)
            dismiss()
        }

    }

}

