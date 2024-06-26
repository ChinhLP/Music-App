package com.example.appmusickotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.databinding.PlaylistItemLayoutBinding
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.util.callBack.OnEditPopupAlbumButtonClickListener
import com.example.appmusickotlin.util.callBack.OnItemClickListener

class AlbumAdapter(
    private val albumList: MutableList<DataListPlayList> ,
    private val listener: OnItemClickListener,
    private val editPopupAlbumButtonClickListener: OnEditPopupAlbumButtonClickListener?,
) : RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {


    inner class AlbumHolder(val binding: PlaylistItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position,albumList[position].id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaylistItemLayoutBinding.inflate(inflater, parent, false)
        return AlbumHolder(binding)
    }



        override fun onBindViewHolder(holder: AlbumHolder, position: Int) {

            val album = albumList[position]
            holder.binding.txtTitle.text = album.title
            //holder.binding.txtNumber.text = numberMusic.toString()?:  "0"

            holder.binding.editButton.setOnClickListener {
                editPopupAlbumButtonClickListener?.onEditPopupAlbumButtonClick(holder.binding.editButton,album.id)
            }
        }

    override fun getItemCount(): Int {
        return albumList.size
    }
}

