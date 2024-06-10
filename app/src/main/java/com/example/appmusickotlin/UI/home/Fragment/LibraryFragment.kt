package com.example.appmusickotlin.UI.home.Fragment

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.appmusickotlin.R
import com.example.appmusickotlin.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentLibraryfragmentBinding


class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryfragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryfragmentBinding.inflate(inflater, container, false)


        val listMusic =  getAllMusic()
        val adapter = MusicAdapter(this.context,listMusic)
        binding.listView.adapter = adapter
        return binding.root
    }

    private fun getAllMusic(): List<Uri> {


        val musicUriList = mutableListOf<Uri>()


        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
        )
        // Thêm điều kiện cho câu truy vấn
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf("1000") // Thời lượng >= 1000ms (1 giây)


        val musicCursor = this.context?.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            selection,
            selectionArgs,
            null
        )

        musicCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)


            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val duration = cursor.getLong(durationColumn)


                // Lấy ảnh album từ ID album


                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id.toLong()
                )

                musicUriList.add(uri)
                // In thông tin âm nhạc ra màn hình
                Log.d("Music", "Id: $id, Title: $title, Artist: $artist, time: $duration")

            }

        }


        musicUriList.sortBy { uri ->
            context?.contentResolver?.query(uri, arrayOf(MediaStore.Audio.Media.TITLE), null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                } else {
                    "" // Trả về chuỗi rỗng nếu không tìm thấy tên bài hát
                }
            }
        }

        return musicUriList
    }



}