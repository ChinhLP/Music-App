package com.example.appmusickotlin.service

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmusickotlin.model.Song

class MusicPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler()
    private var currentSongIndex = -1
    private var playlist: List<Song>? = null


    private val updateInterval = 1000L


    private val _isPrepared = MutableLiveData<Boolean>()
    val isPrepared: LiveData<Boolean> get() = _isPrepared

    private val _isMedia = MutableLiveData<Boolean>()
    val isMedia: LiveData<Boolean> get() = _isMedia

    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition: LiveData<Int> get() = _currentPosition

    init {
        _isPrepared.value = false
        _currentPosition.value = 0
    }


    fun setDataSource(song: Song, ) {
            release()
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
                startUpdatingCurrentPosition()
            }
            mediaPlayer?.setDataSource(context, Uri.parse(song.path))
            mediaPlayer?.prepareAsync()
            _isPrepared.value = true
            _isMedia.value = true
    }


    fun play() {
        if (isPrepared.value == false && mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
            Log.d("eee", "media ${isPrepared.value}")
            _isPrepared.value = true
        }
    }

    fun stopAndRelease() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPrepared.value = false
        _isMedia.value = false
        handler.removeCallbacksAndMessages(null)
    }

    fun pause() {
        mediaPlayer?.pause()
        _isPrepared.value = false
        Log.d("eee", "media ${isPrepared.value}")

    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        _isPrepared.value = false
    }

    private fun startUpdatingCurrentPosition() {
        handler.post(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        _currentPosition.value = it.currentPosition
                    }
                }
                handler.postDelayed(this, updateInterval)
            }
        })
    }

    fun setupMediaPlayerListeners(onCompletion: () -> Unit) {
        mediaPlayer?.setOnCompletionListener {
            onCompletion.invoke()
        }
    }

}