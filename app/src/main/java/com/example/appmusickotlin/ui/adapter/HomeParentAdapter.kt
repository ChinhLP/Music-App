package com.example.appmusickotlin.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.databinding.TopMusicItemLayoutBinding
import com.example.appmusickotlin.model.ChildItem

class HomeParentAdapter(
    private val context: Context?,
    private val parentItemList: ArrayList<Any?>?
) : RecyclerView.Adapter<HomeParentAdapter.HomeParentHolder>() {

    inner class HomeParentHolder(val binding: TopMusicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeParentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TopMusicItemLayoutBinding.inflate(inflater, parent, false)
        return HomeParentHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeParentHolder, position: Int) {
        val item = parentItemList?.get(position)
        Log.d("ArtistData", "+++${parentItemList.toString()}")


        when (item) {
            is ChildItem.TypeAlbum -> {
                holder.binding.txtTopAlbum.text = "Top Albums"
                val albumAdapter = HomeChildAdapter(null , null , null, 1) // Assuming HomeChildAdapter accepts MutableList<Album>
                holder.binding.rccTopMusic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                holder.binding.rccTopMusic.adapter = albumAdapter
            }
            is ChildItem.TypeTracks -> {
                holder.binding.txtTopAlbum.text = "Top Tracks"
                val trackAdapter = HomeChildAdapter(null , null , null, 3) // Assuming HomeChildAdapter accepts MutableList<Track>
                holder.binding.rccTopMusic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                holder.binding.rccTopMusic.adapter = trackAdapter
            }
            is ChildItem.TypeArtist -> {
                holder.binding.txtTopAlbum.text = "Top Artists"
                Log.d("ArtistData", "+++${item.data}")

                val artistAdapter = HomeChildAdapter(null,item.data,null, 2) // Assuming HomeChildAdapter accepts MutableList<Artist>
                holder.binding.rccTopMusic.layoutManager = GridLayoutManager(context,  5, RecyclerView.HORIZONTAL, false)
                holder.binding.rccTopMusic.adapter = artistAdapter

            }
            else -> {
                Log.d("ArtistData", "null -----")

                // Handle other cases or null items as needed
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("99", "${parentItemList?.size}")
        return parentItemList?.size ?: 0
    }
}