package com.example.appmusickotlin.ui.popup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.appmusickotlin.databinding.DialogFragmentLayoutAddMusicBinding
import com.example.appmusickotlin.db.entity.PlaylistEntity
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.util.callBack.PlaylistAddedListener
import com.example.appmusickotlin.db.viewmodel.PlaylistViewModel


class DialogAddPlaylistFragment : DialogFragment() {
    private lateinit var binding : DialogFragmentLayoutAddMusicBinding
    private var playlistAddedListener: PlaylistAddedListener? = null
    private lateinit var playlistViewModel : PlaylistViewModel


    fun setPlaylistAddedListener(listener: PlaylistAddedListener) {
        playlistAddedListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentLayoutAddMusicBinding.inflate(layoutInflater)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvCreate.setOnClickListener{
            val title = binding.edtPlaylistTitle.text.toString()
            val album2 = DataListPlayList(title = title, userId = User.userId!!)
            playlistViewModel.insert(album2)
            playlistAddedListener?.onPlaylistAdded(album2)
            dismiss()
        }
    }


}