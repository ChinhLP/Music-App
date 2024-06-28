package com.example.appmusickotlin.ui.adapter


import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.MusicItemGridLayoutBinding
import com.example.appmusickotlin.databinding.MusicItemLayoutBinding
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.util.callBack.OnEditButtonClickListener
import com.example.appmusickotlin.util.callBack.OnMusicClickListener
import com.example.appmusickotlin.util.formatDuration.formatDuration
import com.example.appmusickotlin.util.shareMusicFile.downloadAndShareMusic
import java.util.Collections

class MusicAdapter(
    private val context: Context?,
    private var musicUriList: MutableList<Song>,
    private val listener: OnEditButtonClickListener,
    private val menu: Boolean,
    private val isGrid: Boolean
    // Biến để đánh dấu kiểu hiển thị là grid hay linear
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Khai báo biến lưu vị trí bài hát được chọn hiện tại và trước đó

    private var songCurrent: Song? = null
    private var isPlay: Boolean? = true

    fun setCurrent(songCurrent: Song) {
        this.songCurrent = songCurrent
        notifyDataSetChanged()
    }

    fun setIsPlay(isPlay: Boolean) {
        this.isPlay = isPlay
        notifyDataSetChanged()

    }

    private var musicClickListener: OnMusicClickListener? = null

    private var swap: Boolean = false

    companion object {
        private const val VIEW_TYPE_LINEAR = 1
        private const val VIEW_TYPE_GRID = 2
    }

    inner class MusicLinearViewHolder(val binding: MusicItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class MusicGridViewHolder(val binding: MusicItemGridLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGrid) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MusicItemGridLayoutBinding.inflate(inflater, parent, false)
            MusicGridViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MusicItemLayoutBinding.inflate(inflater, parent, false)
            MusicLinearViewHolder(binding)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            VIEW_TYPE_LINEAR -> {
                val musicUri = musicUriList[position]
                val linearHolder = holder as MusicLinearViewHolder

                try{
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(musicUri.path)
// Lấy ảnh đại diện album dưới dạng byte array
                    val albumArtBytes = retriever.embeddedPicture
// Để hiển thị ảnh đại diện album, bạn có thể chuyển đổi byte array thành Bitmap
                    if (albumArtBytes != null) {
                        val bitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.size)
                        // Sau đó, bạn có thể hiển thị bitmap này trong ImageView hoặc bất kỳ nơi nào bạn cần
                        linearHolder.binding.avatarImageView.setImageBitmap(bitmap)
                    } else {
                        linearHolder.binding.avatarImageView.setImageResource(R.drawable.lofi_girl_logo)
                    }
// Đừng quên giải phóng các tài nguyên khi bạn đã hoàn thành
                    retriever.release()
                } catch (e: Exception) {
                    Log.e("anh", e.toString())
                }



                // Hiển thị thông tin vào các thành phần UI
                linearHolder.binding.songTitleTextView.text = musicUri.name
                linearHolder.binding.durationTextView.text = musicUri.duration!!.formatDuration()
                linearHolder.binding.subtitleTextView.text = musicUri.artist

                if (swap) {
                    linearHolder.binding.editButton.visibility = View.GONE
                    linearHolder.binding.ibnSwap.visibility = View.VISIBLE

                } else {
                    linearHolder.binding.editButton.visibility = View.VISIBLE
                    linearHolder.binding.ibnSwap.visibility = View.GONE

                }

//                // Xử lý animation
//                if (musicUri == songCurrent&& isPlay == true) {
//                    linearHolder.binding.itemMusic.setBackgroundResource(R.color.DialogBackground)
//                    linearHolder.binding.vAnimation.visibility = View.VISIBLE
//                } else {
//                    linearHolder.binding.itemMusic.setBackgroundResource(0)
//                    linearHolder.binding.vAnimation.visibility = View.GONE
//                }
//



                holder.itemView.setOnClickListener {
                    val previousSong = songCurrent
                    songCurrent = musicUri

                    // Notify item changes for animation
                    previousSong?.let {
                        val previousPosition = musicUriList.indexOf(it)
                        notifyItemChanged(previousPosition)
                    }
                    songCurrent?.let {
                        val currentPosition = musicUriList.indexOf(it)
                        notifyItemChanged(currentPosition)
                    }

                    // Invoke item click listener
                    musicClickListener?.onItemClick(musicUri, musicUriList)
                    Log.d("oqpq", "$songCurrent")
                }


                // Thiết lập sự kiện cho nút chỉnh sửa nếu cần
                linearHolder.binding.editButton.setOnClickListener {
                    showPopupMenu(linearHolder.binding.editButton, musicUri, position)
                }

                // Thiết lập sự kiện cho ibnSwap nếu cần
                linearHolder.binding.ibnSwap.setOnClickListener {
                    // Xử lý khi nút swap được click (nếu có)
                }
            }

            VIEW_TYPE_GRID -> {
                val musicUri = musicUriList[position]
                val gridHolder = holder as MusicGridViewHolder
                try {

                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(musicUri.path)
// Lấy ảnh đại diện album dưới dạng byte array
                    val albumArtBytes = retriever.embeddedPicture
// Để hiển thị ảnh đại diện album, bạn có thể chuyển đổi byte array thành Bitmap
                    if (albumArtBytes != null) {
                        val bitmap =
                            BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.size)
                        // Sau đó, bạn có thể hiển thị bitmap này trong ImageView hoặc bất kỳ nơi nào bạn cần
                        gridHolder.binding.avatarImageView.setImageBitmap(bitmap)
                    } else {
                        gridHolder.binding.avatarImageView.setImageResource(R.drawable.lofi_girl_logo)
                    }
// Đừng quên giải phóng các tài nguyên khi bạn đã hoàn thành
                    retriever.release()
                } catch (e: Exception) {
                    Log.e("anh", e.toString())
                }


                // Hiển thị thông tin vào các thành phần UI
                gridHolder.binding.txtTitle.text = musicUri.name

                gridHolder.binding.txtTime.text = musicUri.duration!!.formatDuration()
                gridHolder.binding.subtitle.text = musicUri.artist

                holder.itemView.setOnClickListener {
                    val song = musicUriList[position]
                    musicClickListener?.onItemClick(song , musicUriList)
                }

                // Thiết lập sự kiện cho nút chỉnh sửa nếu cần
                gridHolder.binding.editButton.setOnClickListener {
                    showPopupMenu(gridHolder.binding.editButton, musicUri, position)
                }


            }
        }
    }

    override fun getItemCount(): Int {
        return musicUriList.size
    }

    fun  updateData(newList: MutableList<Song>) {
        musicUriList = newList
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int): Int {
        return if (isGrid) VIEW_TYPE_GRID else VIEW_TYPE_LINEAR
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSwap(swap: Boolean) {
        this.swap = swap
        notifyDataSetChanged()
    }


    // Hàm để cho phép Swipe và Drag
    fun enableSwipeAndDrag(recyclerView: RecyclerView,swap: Boolean) {
        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN  or ItemTouchHelper.START or ItemTouchHelper.END,
                0 // Không cần vuốt để xóa
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    if (!swap) return false // Không cho phép di chuyển nếu canDrag là false
                    val fromPosition = viewHolder.adapterPosition
                    val toPosition = target.adapterPosition
                    Collections.swap(musicUriList, fromPosition, toPosition)
                    notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // Không xử lý vuốt trong trường hợp này
                }
            })
        itemTouchHelper.attachToRecyclerView(if (swap) recyclerView else null)
    }

    fun setOnMusicClickListener(listener: OnMusicClickListener) {
        this.musicClickListener = listener
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showPopupMenu(view: View, musicUri: Song, position: Int) {
        val popupMenu = PopupMenu(context, view, Gravity.END, 0, R.style.PopupMenu)
        if (menu) {
            popupMenu.menuInflater.inflate(R.menu.menu__popup, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.add -> {
                        listener.onEditButtonClick(musicUri)
                        Toast.makeText(context, "Item 1 clicked", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.share -> {
                        downloadAndShareMusic(context!!, musicUri.path!!, musicUri.name!!)
                        Toast.makeText(context, "Item 2 clicked", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.setForceShowIcon(true)
        } else {
            popupMenu.menuInflater.inflate(R.menu.menu_popup_music_album, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        listener.onDeleteButtonClick(musicUri, position)
                        Toast.makeText(context, "Item 1 clicked", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.share -> {
                        downloadAndShareMusic(context!!, musicUri.path!!, musicUri.name!!)
                        Toast.makeText(context, "Item 2 clicked", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.setForceShowIcon(true)
        }
        popupMenu.show()
    }
}



