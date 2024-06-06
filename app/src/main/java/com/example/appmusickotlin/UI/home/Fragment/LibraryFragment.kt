package com.example.appmusickotlin.UI.home.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appmusickotlin.databinding.FragmentLibraryfragmentBinding


class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryfragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

}