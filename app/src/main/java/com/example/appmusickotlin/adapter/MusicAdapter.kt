package com.example.appmusickotlin.adapter

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ContextMenu
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.util.TimeUtils.formatDuration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.MusicItemLayoutBinding
import com.example.appmusickotlin.model.Song
import com.google.ai.client.generativeai.type.content

class MusicAdapter(private val context: Context?, private val musicUriList: List<Song>) :


    BaseAdapter() {



    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // Giả sử chỉ muốn hiển thị 3 item giống hệt nhau
    override fun getCount(): Int {
        return musicUriList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = MusicItemLayoutBinding.inflate(inflater, parent, false)

        val musicUri = musicUriList[position]



        val sArt = Uri.parse("content://media/external/audio/albumart")
        val uri = ContentUris.withAppendedId(sArt, musicUri.albumId).toString()


        binding.root.context?.let {
            Glide.with(it)
                .load(uri)
                .apply(RequestOptions().placeholder(R.drawable.rectangle).centerCrop())
                .into(binding.avatarImageView)
        }

        // Hiển thị thông tin lấy được vào các thành phần UI
        binding.songTitleTextView.text = musicUri.name
        binding.subtitleTextView.text = "subtitleTextView"
        binding.durationTextView.text = formatDuration(musicUri.duration)


        // Thiết lập sự kiện cho nút chỉnh sửa nếu cần
        binding.editButton.setOnClickListener {
            val popupMenu = PopupMenu(this.context, binding.editButton, Gravity.END, 0, R.style.PopupMenu)
            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.menu__popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->


                when (menuItem.itemId) {
                    R.id.add -> {
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



        return binding.root
    }

    // Hàm này để gắn listener với Activity


    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }



}
