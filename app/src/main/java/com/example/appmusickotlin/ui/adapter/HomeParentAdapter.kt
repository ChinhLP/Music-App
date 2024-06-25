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
import com.example.appmusickotlin.util.callBack.OnSeeALLListener

class HomeParentAdapter(
    private val context: Context?,
    private val parentItemList: ArrayList<Any?>?,
    private val seeAllAlbum : OnSeeALLListener,
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
                val albumAdapter = HomeChildAdapter(item.data, null, null, 1)
                holder.binding.rccTopMusic.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
                holder.binding.rccTopMusic.adapter = albumAdapter
                holder.binding.txtSeeAll.setOnClickListener {
                    seeAllAlbum.onSeeAll(1)
                }
            }
            is ChildItem.TypeTracks -> {
                holder.binding.txtTopAlbum.text = "Top Tracks"
                val trackAdapter = HomeChildAdapter(null, null, item.data, 3)
                holder.binding.rccTopMusic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                holder.binding.rccTopMusic.adapter = trackAdapter
                holder.binding.txtSeeAll.setOnClickListener{
                    seeAllAlbum.onSeeAll(3)

                }
            }
            is ChildItem.TypeArtist -> {
                holder.binding.txtTopAlbum.text = "Top Artists"
                val artistAdapter = HomeChildAdapter(null, item.data, null, 2)
                holder.binding.rccTopMusic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                holder.binding.rccTopMusic.adapter = artistAdapter
                holder.binding.txtSeeAll.setOnClickListener {
                    seeAllAlbum.onSeeAll(2)

                }
            }
            else -> {
                Log.d("ArtistData", "Unknown item type: $item")
                // Handle other cases or null items as needed
            }
        }
    }

    override fun getItemCount(): Int {
        return parentItemList?.size ?: 0
    }
}