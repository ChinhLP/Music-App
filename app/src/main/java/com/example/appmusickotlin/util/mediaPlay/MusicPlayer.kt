package com.example.appmusickotlin.util.mediaPlay

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.widget.SeekBar

class MusicPlayer(private val context: Context, private val seekBar: SeekBar?) {

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler()
    private var isPrepared = false

    init {
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun setDataSource(uri: String) {
        release()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
            isPrepared = true
            seekBar!!.max = mediaPlayer?.duration ?: 0
            updateSeekBar()
        }
        mediaPlayer?.setDataSource(context, Uri.parse(uri))
        mediaPlayer?.prepareAsync()
    }

    fun play() {
        if (isPrepared && mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
            updateSeekBar()
        }
    }

    fun stopAndRelease() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPrepared = false
        handler.removeCallbacksAndMessages(null)
    }

    fun pause() {
        mediaPlayer?.pause()
        handler.removeCallbacksAndMessages(null)
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacksAndMessages(null)
    }

    private fun updateSeekBar() {
        mediaPlayer?.let { player ->
            seekBar!!.progress = player.currentPosition
            if (player.isPlaying) {
                handler.postDelayed({ updateSeekBar() }, 1000)
            }
        }
    }
}