package com.example.appmusickotlin.UI.home.Fragment

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.appmusickotlin.R
import com.example.appmusickotlin.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentLibraryfragmentBinding
import com.example.appmusickotlin.model.Song
import java.util.TreeSet


class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryfragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryfragmentBinding.inflate(inflater, container, false)


        val listMusic =  getAllMusic()
        val adapter = MusicAdapter(this.context,listMusic)
        val musicAdapter = MusicAdapter(this.context, listMusic)

        binding.listView.adapter = adapter
        return binding.root
    }

    private fun getAllMusic(): List<Song> {


        val musicUriList = mutableListOf<Song>()


        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
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
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)



            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val title = cursor.getString(titleColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)

                // Lấy ảnh album từ ID album

                val song = Song(id,title,duration,albumId)


                musicUriList.add(song)
                // In thông tin âm nhạc ra màn hình
                Log.d("Music", "Id: $id, Title: $title, Artist: $albumId, time: $duration")

            }

        }

// Sắp xếp danh sách theo thứ tự alphabetic của name
        musicUriList.sortBy { it.name }

        return musicUriList
    }
}
