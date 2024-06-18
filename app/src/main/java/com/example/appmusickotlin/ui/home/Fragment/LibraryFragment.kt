package com.example.appmusickotlin.ui.home.Fragment

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.contendProvider.MusicLoader
import com.example.appmusickotlin.ui.diaglogs.DialogAddLibraryFragment
import com.example.appmusickotlin.ui.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentLibraryfragmentBinding
import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.db.entity.MusicLocal
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.ui.authetication.viewmodel.AuthViewModel
import com.example.appmusickotlin.ui.home.viewmodel.HomeViewModel
import com.example.appmusickotlin.util.callBack.OnEditButtonClickListener
import com.example.appmusickotlin.util.callBack.OnMusicClickListener
import com.example.appmusickotlin.viewmodel.MusicLocalViewModel
import com.example.appmusickotlin.viewmodel.MusicViewModel
import com.example.appmusickotlin.viewmodel.PlaylistViewModel
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator




class LibraryFragment : Fragment(), OnEditButtonClickListener, OnMusicClickListener {

    private lateinit var binding: FragmentLibraryfragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var musicViewModel: MusicViewModel
    private lateinit var playlistViewModel : PlaylistViewModel
    private lateinit var musicLocalViewModel : MusicLocalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryfragmentBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager  = LinearLayoutManager(this.context)
        musicViewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        musicLocalViewModel = ViewModelProvider(requireActivity()).get(MusicLocalViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMusic =  MusicLoader(requireContext()).getAllMusic()

        val adapter = MusicAdapter(this.context,listMusic,this,true,false)

        adapter.setOnMusicClickListener(this)
        binding.recyclerView.adapter = adapter

        binding.btnLeft.setOnClickListener {
            binding.recyclerView.itemAnimator = SlideInLeftAnimator()
        }
        binding.btnRight.setOnClickListener {
            binding.recyclerView.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))

        }
    }


    override fun onEditButtonClick(song: Song ) {

        val addDialog = DialogAddLibraryFragment()
        val args = Bundle()
        args.putSerializable("song", song) // Đặt đối tượng Song vào Bundle
        // Đặt Bundle vào DialogFragment
        addDialog.arguments = args

        addDialog.show(childFragmentManager,"Add Dialog")
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {
    }

    override fun onItemClick(song: Song) {
        //viewModel.setSongAlbum(song)
        Log.d("ppp", "onItemClick")
    }


}