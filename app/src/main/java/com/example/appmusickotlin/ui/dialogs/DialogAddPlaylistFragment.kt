package com.example.appmusickotlin.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appmusickotlin.databinding.DialogFragmentLayoutAddMusicBinding
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.ui.home.Fragment.PlaylistAddedListener


class DialogAddPlaylistFragment : DialogFragment() {
    private lateinit var binding : DialogFragmentLayoutAddMusicBinding
    private var playlistAddedListener: PlaylistAddedListener? = null


    fun setPlaylistAddedListener(listener: PlaylistAddedListener) {
        playlistAddedListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentLayoutAddMusicBinding.inflate(layoutInflater)
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvCreate.setOnClickListener{
            val title = binding.edtPlaylistTitle.text.toString()
            val album = DataListPlayList(title)
            playlistAddedListener?.onPlaylistAdded(album)
            dismiss()
        }
        return binding.root
    }


}