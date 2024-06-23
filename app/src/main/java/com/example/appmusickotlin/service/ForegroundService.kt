package com.example.appmusickotlin.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmusickotlin.model.Song


class ForegroundService : Service() {

    private lateinit var musicPlayer: MusicPlayer
    private lateinit var notificationManager: NotificationManager
    val currentPosition: LiveData<Int>
        get() = musicPlayer.currentPosition
    val isPrepared: LiveData<Boolean>
        get() = musicPlayer.isPrepared
    val isMedia: LiveData<Boolean> get() = musicPlayer.isMedia

    private val _song = MutableLiveData<Song>()
    val song: LiveData<Song> get() = _song

    private var listMusic : MutableList<Song>? = mutableListOf()
    private var musicIndex: Int = 0

    companion object {
        private const val NOTIFICATION_ID = 1
    }


    inner class LocalBinder : Binder() {
        fun getService(): ForegroundService = this@ForegroundService

    }

    // Khai báo một biến để lưu trữ đối tượng Binder
    private val binder = LocalBinder()

    // Phương thức công khai để trả về đối tượng Binder
    override fun onBind(p0: Intent?): IBinder {

        return binder
    }


    override fun onCreate() {
        super.onCreate()

        musicPlayer = MusicPlayer(this)
        notificationManager = NotificationManager(this)

        // Create and display the foreground notification
        val notification = notificationManager.buildNotification("Service is running")
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        when (intent!!.action) {
            "ACTION_LOAD_LIST" -> handleACtionLoadList(intent)
            "ACTION_RESUME" -> handleActionResume(intent)
            "ACTION_PAUSE" -> handleActionPause()
            "ACTION_PLAY" -> handleActionPlay()
            "ACTION_CLOSE" -> handleActionClose()
            "ACTION_MIX" -> handleActionMix()
            "ACTION_LOOP" -> handleActionLoop()
            "ACTION_NEXT" -> handleActionNext()
            "ACTION_PREVIOUS" -> handleActionPrevious()
        }
        return START_NOT_STICKY
    }

    private fun handleACtionLoadList(intent : Intent?) {
        listMusic = (intent?.getSerializableExtra("listSong") as? MutableList<Song>)!!
    }

    private fun handleActionPrevious() {
        musicIndex = if (musicIndex == 0) {
            listMusic!!.size - 1
        } else {
            musicIndex - 1
        }
        playCurrentSong()
        Log.d("ddds", "$musicIndex ---- ${listMusic!![musicIndex]}")
    }

    private fun handleActionNext() {
        musicIndex = if (musicIndex + 1 < listMusic!!.size) {
            musicIndex + 1
        } else {
            0
        }
        playCurrentSong()
        Log.d("ddds", "$musicIndex ---- ${listMusic!![musicIndex]}")
    }

    private fun handleActionMix(){
    }

    private fun handleActionLoop(){

    }

    private fun handleActionResume(intent: Intent?) {
        val song = (intent?.getSerializableExtra("song") as? Song) ?: return
        musicIndex = findPositionById(song.id).toInt()
        //Log.d("dddd", "${listMusic!![musicIndex]} trong danh sách.")

        if (musicIndex == -1) {
            Log.d("dddd", "Không tìm thấy bài hát có ID ${song.id} trong danh sách.")
            return
        }
        playCurrentSong()
    }

    private fun handleActionPause() {
        musicPlayer.pause()
    }

    private fun handleActionPlay() {

        musicPlayer.play()
    }

    private fun handleActionClose() {
        musicPlayer.stopAndRelease()
    }

    private fun playCurrentSong() {
        _song.value = listMusic?.get(musicIndex)
        //Log.d("dddd", "${listMusic!![musicIndex]} trong danh sách.")

        musicPlayer.stopAndRelease()
        musicPlayer.setDataSource(listMusic!![musicIndex])
        musicPlayer.setupMediaPlayerListeners {
            handleActionNext()  // Chuyển sang bài hát tiếp theo khi bài hát hiện tại kết thúc
        }
    }
    fun findPositionById(musicId: Long): Long {
        listMusic?.let { list ->
            for (index in list.indices) {
                if (list[index].id == musicId) {
                    // Nếu tìm thấy bài hát có musicId, di chuyển nó lên đầu danh sách
                    return index.toLong() // Trả về vị trí của bài hát trong danh sách
                }
            }
        }
        return -1 // Nếu không tìm thấy, trả về -1
    }


    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }


}