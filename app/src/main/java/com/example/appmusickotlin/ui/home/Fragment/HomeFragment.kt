package com.example.appmusickotlin.ui.home.Fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.AlbumViewModel
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.ArtistViewModel
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.TrackViewModel
import com.example.appmusickotlin.ui.viewModel.MediaViewModel
import com.example.appmusickotlin.databinding.FragmentHomefragmentBinding
import com.example.appmusickotlin.model.ChildItem
import com.example.appmusickotlin.ui.adapter.HomeParentAdapter

//import com.example.appmusickotlin.model.User
//import com.example.appmusickotlin.model.saveUser
//import com.example.appmusickotlin.model.setMyUser


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomefragmentBinding
    private val parentItemList = ArrayList<Any?>()


    private lateinit var artistsViewModel: ArtistViewModel
    private lateinit var albumsViewModel: AlbumViewModel
    private lateinit var tracksViewModel: TrackViewModel
    private var successCount = 0


    private var isPlay: Boolean = false


    // sử dung broadcastReceiver
    private val playBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isPlay = intent?.getBooleanExtra("isPlaying", false) ?: false
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.example.syncbuttons.PLAY_ACTION")
        requireContext().registerReceiver(playBroadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(playBroadcastReceiver)
    }

    private fun sendPlayStateToActivity() {
        val intent = Intent("com.example.syncbuttons.PLAY_ACTION")
        intent.putExtra("isPlaying", isPlay)
        requireContext().sendBroadcast(intent)
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
            method = "chart.gettopartists",
        )




        val parentAdapter = HomeParentAdapter(requireContext(), parentItemList)
        binding.rccParent.layoutManager = LinearLayoutManager(requireContext())
        binding.rccParent.adapter = parentAdapter


        albumsViewModel.album.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is State.Success -> {
                    val albumList = state.data
                    Log.d("YourFragment", "Success---: $albumList")
                    parentItemList.removeAll {it is ChildItem.TypeAlbum}

                    if(albumList != null&& albumList.isNotEmpty()){
                        parentItemList.add(0, ChildItem.TypeAlbum(albumList))
                        parentAdapter.notifyDataSetChanged()
                    }
                    successCount++
                    checkAllLoaded()

                }
                is State.Error -> {
                    val errorMessage = state.exception
                    Log.e("YourFragment", "Error: $errorMessage")
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

                    if(trackList != null && trackList.isNotEmpty()){
                        parentItemList.add(ChildItem.TypeTracks(trackList))
                        parentAdapter.notifyDataSetChanged()
                    }
                    successCount++
                    checkAllLoaded()
                }
                is State.Error -> {
                    val errorMessage = state.exception
                    Log.e("YourFragment", "Error: $errorMessage")
                    // Handle error state
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


//    private fun BtnTheme() {
//        binding.btnTheme.setOnClickListener {
//
////            // Kiểm tra chủ đề hiện tại của thiết bị
////            val uiMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
////
////            // Nếu đang ở chủ đề tối, chuyển sang chủ đề sáng và ngược lại
////            val newMode = if (uiMode == Configuration.UI_MODE_NIGHT_YES) {
////                AppCompatDelegate.MODE_NIGHT_NO // Chủ đề sáng
////            } else {
////                AppCompatDelegate.MODE_NIGHT_YES // Chủ đề tối
////            }
////
////            // Đặt chế độ chủ đề mới cho thiết bị
////            AppCompatDelegate.setDefaultNightMode(newMode)
//        }
//    }
//    private fun btnBack() {
//        binding.btnBack.setOnClickListener {
//            val intent = Intent(requireActivity(), AuthActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//
//        }
//    }
//    private fun btnLw() {
//        binding.btnLw.setOnClickListener {
//
//            // Lấy ngôn ngữ hiện tại của thiết bị
//            val currentLocale = Locale.getDefault()
//            val currentLanguage = currentLocale.language
//            var locale = Locale.getDefault()
//            if (currentLanguage == "en") {
//                // Chuyển từ tiếng Anh sang tiếng Việt
//                Locale.setDefault(Locale("vi"))
//                locale = Locale("vi")
//
//            } else if (currentLanguage == "vi") {
//                // Chuyển từ tiếng Việt sang tiếng Anh
//                Locale.setDefault(Locale("en"))
//                locale = Locale("en")
//
//            }
//
//            // Cập nhật cấu hình ngôn ngữ của tài nguyên
//            val config = Configuration()
//            config.locale = locale
//            resources.updateConfiguration(config, resources.displayMetrics)
//            requireActivity().recreate() // Tái khởi động Activity để áp dụng thay đổi ngôn ngữ
//
//        }
//    }
//


}