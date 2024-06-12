package com.example.appmusickotlin.adapter

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.MusicItemLayoutBinding
import com.example.appmusickotlin.model.Song

class MusicAdapter(
    private val context: Context?,
    private val musicUriList: MutableList<Song>,
    private val listener: OnEditButtonClickListener
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(val binding: MusicItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Không cần khai báo các thành phần giao diện người dùng tại đây
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MusicItemLayoutBinding.inflate(inflater, parent, false)
        return MusicViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {

        val musicUri = musicUriList[position]


        val sArt = Uri.parse("content://media/external/audio/albumart")
        val uri = ContentUris.withAppendedId(sArt, musicUri.albumId).toString()


        holder.binding.root.context?.let {
            Glide.with(it)
                .load(uri)
                .apply(RequestOptions().placeholder(R.drawable.rectangle).centerCrop())
                .into(holder.binding.avatarImageView)
        }

        // Hiển thị thông tin lấy được vào các thành phần UI
        holder.binding.songTitleTextView.text = musicUri.name
        holder.binding.subtitleTextView.text = "subtitleTextView"
        holder.binding.durationTextView.text = formatDuration(musicUri.duration)


        // Thiết lập sự kiện cho nút chỉnh sửa nếu cần
        holder.binding.editButton.setOnClickListener {
            val popupMenu =
                PopupMenu(
                    this.context,
                    holder.binding.editButton,
                    Gravity.END,
                    0,
                    R.style.PopupMenu
                )
            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.menu__popup, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.add -> {

                        listener.onEditButtonClick(musicUri)
                        // Xử lý khi MenuItem 1 được chọn
                        Toast.makeText(context, "Item 1 clicked", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.share -> {
                        // Xử lý khi MenuItem 2 được chọn
                        Toast.makeText(context, "Item 2 clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    // Xử lý cho các mục menu khác nếu cần
                    else -> false
                }
            }
            popupMenu.setForceShowIcon(true)
            // Showing the popup menu
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return musicUriList.size
    }

    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun addItem(item: Song,position: Int) {
        musicUriList.add(position,item)
        notifyItemInserted(position)
    }
    fun removeItem(position: Int) {
        musicUriList.removeAt(position)
        notifyItemRemoved(position)
    }

}

interface OnEditButtonClickListener {
    fun onEditButtonClick(song: Song)
}