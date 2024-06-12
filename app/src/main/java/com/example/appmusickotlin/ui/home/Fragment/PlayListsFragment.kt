package com.example.appmusickotlin.ui.home.Fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.appmusickotlin.databinding.FragmentPlaylistsfragmentBinding


class PlayListsFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistsfragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistsfragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddPlaylist.setOnClickListener {
            //AddPlaylistDialog()
        }
    }

    private fun AddPlaylistDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("New Playlist")

        val editText = EditText(requireContext())


        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        editText.layoutParams = layoutParams

        // Đặt hint cho EditText
        editText.hint = "Nhập nội dung"

        // Thêm EditText vào AlertDialog
        alertDialogBuilder.setView(editText)



        alertDialogBuilder.setPositiveButton("Create") {
            dialog, which ->

        }
        alertDialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->

        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}