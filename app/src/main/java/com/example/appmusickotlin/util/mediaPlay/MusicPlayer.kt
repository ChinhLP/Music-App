package com.example.appmusickotlin.util.mediaPlay

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.widget.SeekBar

class MusicPlayer(private val context: Context, private val seekBar: SeekBar) {

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private val handler = Handler()

    init {
        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            updateSeekBar()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun setDataSource(uri: String) {
        mediaPlayer.setDataSource(context, Uri.parse(uri))
        mediaPlayer.prepareAsync()
    }

    fun play() {
            mediaPlayer.start()
            updateSeekBar()

    }

    fun stopAndRelease() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    fun release() {
    }

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition
        if (mediaPlayer.isPlaying) {
            handler.postDelayed({ updateSeekBar() }, 1000)
        }
    }
}