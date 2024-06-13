package com.example.appmusickotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.databinding.PlaylistItemLayoutBinding
import com.example.appmusickotlin.model.DataListPlayList

class AlbumAdapter(
    private val albumList: MutableList<DataListPlayList>,
    private val listener: OnItemClickListener,

) : RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {

    inner class AlbumHolder(val binding: PlaylistItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.AlbumHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaylistItemLayoutBinding.inflate(inflater, parent, false)
        return AlbumHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumHolder, position: Int) {
        val album = albumList[position]
        holder.binding.txtTitle.text = album.title
        holder.binding.txtNumber.text = album.listMusic?.size?.toString() ?: "0"
    }

    override fun getItemCount(): Int {
        return albumList.size
    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)

}