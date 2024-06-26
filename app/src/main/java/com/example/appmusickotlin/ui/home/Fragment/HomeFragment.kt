package com.example.appmusickotlin.ui.home.Fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appmusickotlin.R
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.AlbumViewModel
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.ArtistViewModel
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.TrackViewModel
import com.example.appmusickotlin.databinding.FragmentHomefragmentBinding
import com.example.appmusickotlin.model.ChildItem
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.SeeAll.SeeAllActivity
import com.example.appmusickotlin.ui.adapter.HomeParentAdapter
import com.example.appmusickotlin.ui.profile.ProfileActivity
import com.example.appmusickotlin.ui.setting.SettingActivity
import com.example.appmusickotlin.util.callBack.OnSeeALLListener

//import com.example.appmusickotlin.model.User
//import com.example.appmusickotlin.model.saveUser
//import com.example.appmusickotlin.model.setMyUser


class HomeFragment : Fragment() , OnSeeALLListener {

    private lateinit var binding: FragmentHomefragmentBinding
    private val parentItemList = ArrayList<Any?>()


    private lateinit var artistsViewModel: ArtistViewModel
    private lateinit var albumsViewModel: AlbumViewModel
    private lateinit var tracksViewModel: TrackViewModel
    private var successCount = 0
    private var errorCount = 0


    private var isPlay: Boolean = false



    override fun onResume() {
        super.onResume()

        if(User.imageAvatar != ""){
            Glide.with(this)
                .load(User.imageAvatar)
                .placeholder(R.drawable.profile) // Hình ảnh hiển thị trong khi chờ tải
                .error(R.drawable.rain) // Hình ảnh hiển thị khi tải thất bại
                .into(binding.imvAvatar)
        }

        binding.txtUsername.setText(User.username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomefragmentBinding.inflate(inflater, container, false)
        artistsViewModel = ViewModelProvider(requireActivity()).get(ArtistViewModel::class.java)
        albumsViewModel = ViewModelProvider(requireActivity()).get(AlbumViewModel::class.java)
        tracksViewModel = ViewModelProvider(requireActivity()).get(TrackViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getData()
        if(User.imageAvatar != ""){
            Glide.with(this)
                .load(User.imageAvatar)
                .placeholder(R.drawable.profile) // Hình ảnh hiển thị trong khi chờ tải
                .error(R.drawable.rain) // Hình ảnh hiển thị khi tải thất bại
                .into(binding.imvAvatar)
        }


        binding.txtUsername.setText(User.username)

        binding.imvAvatar.setOnClickListener {
            val intent = Intent(requireActivity(),ProfileActivity::class.java)
            startActivity(intent)
        }

        val parentAdapter = HomeParentAdapter(requireContext(), parentItemList,this)
        binding.rccParent.layoutManager = LinearLayoutManager(requireContext())
        binding.rccParent.adapter = parentAdapter



        albumsViewModel.album.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is State.Success -> {
                    val albumList = state.data
                    Log.d("YourFragment", "Success---: $albumList")
                    parentItemList.removeAll {it is ChildItem.TypeAlbum}

                    if(albumList != null && albumList.isNotEmpty()){
                        parentItemList.add(0, ChildItem.TypeAlbum(albumList))
                        parentAdapter.notifyDataSetChanged()
                    }
                    successCount++
                    Log.d("YourFragment", "Success---: $successCount")
                    checkAllLoaded()

                }
                is State.Error -> {
                    val errorMessage = state.exception
                    Log.e("YourFragment", "Error: $errorMessage")
                    errorCount++
                    checkError()
                    // Handle error state
                }
                is State.Loading -> {
                    Log.d("YourFragment", "Loading...")
                }
                null -> {
                    Log.e("YourFragment", "Error: State is null")
                }
            }
            sortParentItemList()
            parentAdapter.notifyDataSetChanged()
        })

        tracksViewModel.track.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is State.Success -> {
                    val trackList = state.data
                    parentItemList.removeAll { it is ChildItem.TypeTracks }
                    Log.d("YourFragment", "Success---: $trackList")

                    if(trackList != null && trackList.isNotEmpty()){
                        parentItemList.add(ChildItem.TypeTracks(trackList))
                        parentAdapter.notifyDataSetChanged()
                    }
                    successCount++
                    Log.d("YourFragment", "Success---: $successCount")

                    checkAllLoaded()
                }
                is State.Error -> {
                    val errorMessage = state.exception
                    Log.e("YourFragment", "Error: $errorMessage")
                    // Handle error state
                    errorCount++
                    checkError()
                }
                is State.Loading -> {
                    // Handle loading state
                    Log.d("YourFragment", "Loading...")
                }
                null -> {
                    Log.e("YourFragment", "Error: State is null")
                    // Handle null state if needed
                }
            }
            sortParentItemList()
            parentAdapter.notifyDataSetChanged()
        })
        artistsViewModel.artist.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Success -> {
                    val artistList = state.data
                    Log.d("YourFragment", "Success: $artistList")
                    parentItemList.removeAll { it is ChildItem.TypeArtist }

                    if (artistList != null&& artistList.isNotEmpty()) {
                        parentItemList.add(ChildItem.TypeArtist(artistList))
                        parentAdapter.notifyDataSetChanged()
                    }
                    successCount++

                    checkAllLoaded()
                }
                is State.Error -> {
                    val errorMessage = state.exception
                    Log.e("YourFragment", "Error: $errorMessage")
                    // Handle error state
                    errorCount++
                    Log.d("YourFragment", "Success---: $successCount")

                    checkError()
                }
                is State.Loading -> {
                    // Handle loading state
                    Log.d("YourFragment", "Loading...")
                }
                null -> {
                    Log.e("YourFragment", "Error: State is null")
                    // Handle null state if needed
                }
            }
            sortParentItemList()
            parentAdapter.notifyDataSetChanged()

        })

        binding.btnTryAgain.setOnClickListener {
            binding.vAnimation.visibility = View.VISIBLE
            binding.grNoInternet.visibility = View.GONE
            getData()
            checkError()
        }

        binding.imbSettings.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        // không cho back lại
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })


    }
    private fun getData(){
        successCount = 0
        errorCount = 0

        albumsViewModel.getTop6Albums(
            apiKey = "e65449d181214f936368984d4f4d4ae8",
            format = "json",
            method = "artist.getTopAlbums",
            mbid = "f9b593e6-4503-414c-99a0-46595ecd2e23"
        )
        tracksViewModel.getTop5tracks(
            apiKey = "e65449d181214f936368984d4f4d4ae8",
            format = "json",
            method = "artist.getTopTracks",
            mbid = "f9b593e6-4503-414c-99a0-46595ecd2e23"
        )
        artistsViewModel.fetchTop5Artists(
            apiKey = "e65449d181214f936368984d4f4d4ae8",
            format = "json",
            method = "chart.gettopartists"
        )

    }
    private fun sortParentItemList() {
        parentItemList.sortBy {
            when (it) {
                is ChildItem.TypeAlbum -> 0
                is ChildItem.TypeTracks -> 1
                is ChildItem.TypeArtist -> 2
                else -> 3
            }
        }
    }
    private fun checkAllLoaded() {
        if (successCount == 3) {
            // Ẩn cycle loader và hiển thị RecyclerView
            binding.vAnimation.visibility = View.GONE
            binding.rccParent.visibility = View.VISIBLE
        }
    }
    private fun checkError() {
        if(errorCount != 0){
            binding.vAnimation.visibility = View.GONE
            binding.grNoInternet.visibility = View.VISIBLE
        }
    }

    override fun onSeeAll(type : Int) {
        val intent = Intent(requireActivity(), SeeAllActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }



}