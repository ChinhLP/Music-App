package com.example.appmusickotlin.data.local.contendProvider

import android.content.Context
import android.provider.MediaStore
import com.example.appmusickotlin.model.Song

class MusicLoader(private val context: Context) {

    fun getAllMusic(): MutableList<Song> {
        val musicUriList = mutableListOf<Song>()

        val musicProjection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
        )

        // Thêm điều kiện cho câu truy vấn
        val selection = "${MediaStore.Audio.Media.DURATION} >= ? AND ${MediaStore.Audio.Media.MIME_TYPE} = ?"
        val selectionArgs = arrayOf("1000", "audio/mpeg") // Thời lượng >= 1000ms (1 giây)

        val musicCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            selection,
            selectionArgs,
            null
        )

        musicCursor?.use { cursor ->
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                val title = cursor.getString(titleColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val artist = cursor.getString(artistColumn)
                val data = cursor.getString(dataColumn)

                val song = Song(name = title, duration = duration, albumId = albumId, artist =  artist, path = data)
                musicUriList.add(song)
            }
        }

        // Sắp xếp danh sách theo thứ tự alphabetic của name
        musicUriList.sortBy { it.name }

        return musicUriList
    }
}