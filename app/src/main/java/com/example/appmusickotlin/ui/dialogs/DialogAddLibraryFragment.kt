package com.example.appmusickotlin.ui.diaglogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appmusickotlin.R
import com.example.appmusickotlin.ui.home.HomeScreenActivity
import com.example.appmusickotlin.databinding.FragmentDialogAddLibraryBinding
import com.example.appmusickotlin.ui.home.Fragment.PlayListsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class DialogAddLibraryFragment : DialogFragment(){

    private lateinit var binding: FragmentDialogAddLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddLibraryBinding.inflate(layoutInflater)

        binding.btnAddmusic.setOnClickListener {

            val newFragment = PlayListsFragment()

            // Use the parent fragment manager to handle the fragment transaction
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()

            val navigationBottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            navigationBottom.menu.findItem(R.id.btnPlaylist).setChecked(true)
            // Đóng DialogFragment sau khi chuyển sang Fragment mới (tuỳ chọn)
            dismiss()
        }
        return  binding.root

    }

}