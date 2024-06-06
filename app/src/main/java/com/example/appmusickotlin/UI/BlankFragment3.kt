package com.example.appmusickotlin.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.FragmentBlank2Binding
import com.example.appmusickotlin.databinding.FragmentBlank3Binding

private lateinit var binding: FragmentBlank3Binding

class BlankFragment3 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlank3Binding.inflate(inflater, container, false)

        return binding.root
    }

}