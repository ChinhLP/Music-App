package com.example.appmusickotlin.ui.adapter

import android.graphics.Color
import android.util.Log
import android.util.Printer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusickotlin.R
import com.example.appmusickotlin.data.remoteRetrofit.model.Album
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.Track
import com.example.appmusickotlin.databinding.PlaylistItemLayoutBinding
import com.example.appmusickotlin.databinding.TopAlbumItemLayoutBinding
import com.example.appmusickotlin.databinding.TopArtistItemLayoutBinding
import com.example.appmusickotlin.databinding.TopTracksItemLayoutBinding

class HomeChildAdapter(
    private var albumList: MutableList<Album>? = null,
    private var artistList: MutableList<Artist>? = null,
    private var trackList: MutableList<Track>? = null,
    private var type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class AlbumViewHolder(val binding: TopAlbumItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ArtistViewHolder(val binding: TopArtistItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TrackViewHolder(val binding: TopTracksItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            1 -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopAlbumItemLayoutBinding.inflate(inflater, parent, false)
                AlbumViewHolder(binding)
            }
            3 -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopTracksItemLayoutBinding.inflate(inflater, parent, false)
                TrackViewHolder(binding)
            }
            2 -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopArtistItemLayoutBinding.inflate(inflater, parent, false)
                ArtistViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $type")
        }
    }

    override fun getItemCount(): Int {
        return when (type) {
            1 -> albumList?.size ?: 0
            2 -> artistList?.size ?: 0
            3 -> trackList?.size ?: 0
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                val albumItem = albumList!![position]
                val imageAlbum = albumItem.image?.get(3)?.text
                val albumViewHolder = holder as AlbumViewHolder

                albumViewHolder.binding.root.context?.let {
                    Glide.with(it)
                        .load(imageAlbum)
                        .apply(RequestOptions().placeholder(R.drawable.samurai).centerCrop())
                        .into(albumViewHolder.binding.imvLogo)
                }
                albumViewHolder.binding.txtTitle.text = albumItem.name
                albumViewHolder.binding.txtNumber.text = albumItem.artist?.name ?: ""
                // You may need to bind other properties as well
            }
            3 -> {
                val trackItem = trackList!![position]
                val imageTrack = trackItem.image?.get(3)?.text

                val trackViewHolder = holder as TrackViewHolder
                trackViewHolder.binding.root.context?.let {
                    Glide.with(it)
                        .load(imageTrack)
                        .apply(RequestOptions().placeholder(R.drawable.avatar_track).centerCrop())
                        .into(trackViewHolder.binding.avatarImageView)
                }
                when (position% 8){
                    0 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#FF7777"))
                    1 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#FFFA77"))
                    2 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#4462FF"))
                    3 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#14FF00"))
                    4 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#E231FF"))
                    5 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#00FFFF"))
                    6 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#FB003C"))
                    7 -> trackViewHolder.binding.viewColor.setBackgroundColor(Color.parseColor("#F2A5FF"))
                }

                trackViewHolder.binding.songTitleTextView.text = trackItem.name
                trackViewHolder.binding.txtNumberPlay.text = trackItem.playcount.toString()
                trackViewHolder.binding.txtListeners.text = trackItem.listeners.toString()
                // You may need to bind other properties as well
            }
            2 -> {
                val artistItem = artistList!![position]
                val imageArtist = artistItem.image?.get(3)?.text
                val artistViewHolder = holder as ArtistViewHolder
                artistViewHolder.binding.root.context?.let {
                    Glide.with(it)
                        .load(imageArtist)
                        .apply(RequestOptions().placeholder(R.drawable.top_artist).centerCrop())
                        .into(artistViewHolder.binding.avatarImageView)
                }
                artistViewHolder.binding.songTitleTextView.text = artistItem.name
            }
        }
    }
}