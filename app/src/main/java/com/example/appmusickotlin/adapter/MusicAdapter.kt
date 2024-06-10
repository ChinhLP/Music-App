package com.example.appmusickotlin.adapter

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.util.TimeUtils.formatDuration
import com.bumptech.glide.Glide
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.MusicItemLayoutBinding
import com.google.ai.client.generativeai.type.content

class MusicAdapter(private val context: Context?,private val musicUriList: List<Uri>) : BaseAdapter() {


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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = MusicItemLayoutBinding.inflate(inflater, parent, false)


        val musicUri = musicUriList[position]



        // Lấy thông tin từ MediaStore bằng cách truy vấn thông tin của URI
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION
        )

        context?.contentResolver?.query(musicUri,projection,null,null,null)?.use {
                cursor  ->
            if ( cursor.moveToFirst()){
                val title = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val duration = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)




                binding.avatarImageView.setImageResource(R.drawable.rectangle) // Thay bằng hình ảnh của bạn
                // Hiển thị thông tin lấy được vào các thành phần UI
                binding.songTitleTextView.text = cursor.getString(title)
                binding.subtitleTextView.text = "subtitleTextView"
                binding.durationTextView.text  = formatDuration(cursor.getLong(duration))

            }
        }



        // Thiết lập sự kiện cho nút chỉnh sửa nếu cần
        binding.editButton.setOnClickListener {
            // Xử lý sự kiện khi nhấn nút chỉnh sửa
        }

        return binding.root
    }
    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}