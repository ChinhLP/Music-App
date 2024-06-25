package com.example.appmusickotlin.ui.SeeAll

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.AlbumViewModel
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.ArtistViewModel
import com.example.appmusickotlin.data.remoteRetrofit.viewmodel.TrackViewModel
import com.example.appmusickotlin.databinding.ActivitySeeAllBinding
import com.example.appmusickotlin.ui.adapter.HomeChildAdapter

class SeeAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeeAllBinding
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var trackViewModel : TrackViewModel
    private lateinit var artistViewModel: ArtistViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeeAllBinding.inflate(layoutInflater)
        albumViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        trackViewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
        artistViewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        setContentView(binding.root)
        val receivedIntent = intent
        if (receivedIntent.hasExtra("type")) {
            val intValue = receivedIntent.getIntExtra("type", 0)
            when (intValue) {
                1 -> {
                    binding.txtTopAlbum.text = "Top Albums"
                    albumViewModel.getAllAlbum(
                        apiKey = "e65449d181214f936368984d4f4d4ae8",
                        format = "json",
                        method = "artist.getTopAlbums",
                        mbid = "f9b593e6-4503-414c-99a0-46595ecd2e23"
                    )

                    albumViewModel.allAlbum.observe(this, Observer { state ->
                        when(state) {
                            is State.Success -> {
                                val albumList = state.data

                                if(albumList != null&& albumList.isNotEmpty()){
                                    val allAlbumsAdapter = HomeChildAdapter(albumList,null,null,1)
                                    allAlbumsAdapter.notifyDataSetChanged()
                                    binding.rccAllAlbum.adapter = allAlbumsAdapter
                                    binding.rccAllAlbum.layoutManager = LinearLayoutManager(this)

                                }
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
                    })
                }

                2 -> {
                    binding.txtTopAlbum.text = "Top Artist"

                    artistViewModel.fetchTopArtists(
                        apiKey = "e65449d181214f936368984d4f4d4ae8",
                        format = "json",
                        method = "chart.gettopartists"
                    )
                    artistViewModel.allArtists.observe(this, Observer { state ->

                        when(state) {
                            is State.Success -> {
                                val artistList = state.data
                                Log.e("YourFragment", "Error: $artistList")

                                if(artistList != null&& artistList.isNotEmpty()){
                                    val allArtistAdapter = HomeChildAdapter(null,artistList,null,2)
                                    allArtistAdapter.notifyDataSetChanged()
                                    binding.rccAllAlbum.adapter = allArtistAdapter
                                    binding.rccAllAlbum.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

                                }
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
                    })
                }

                3 -> {
                    binding.txtTopAlbum.text = "Top Tracks"

                    trackViewModel.getAllTracks(
                        apiKey = "e65449d181214f936368984d4f4d4ae8",
                        format = "json",
                        method = "artist.getTopTracks",
                        mbid = "f9b593e6-4503-414c-99a0-46595ecd2e23"
                    )
                    trackViewModel.allTrack.observe(this, Observer { state ->

                        when(state) {
                            is State.Success -> {
                                val trackList = state.data

                                if(trackList != null&& trackList.isNotEmpty()){
                                    val allTrackAdapter = HomeChildAdapter(null,null,trackList,3)
                                    allTrackAdapter.notifyDataSetChanged()
                                    binding.rccAllAlbum.adapter = allTrackAdapter
                                    binding.rccAllAlbum.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

                                }
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
                    })
                }
            }
        }

        binding.imbBack.setOnClickListener {
            finish()
        }

    }
}