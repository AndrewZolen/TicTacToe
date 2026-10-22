package com.github.andrewzolen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.andrewzolen.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {


    private val humanSymbol : Cell = Cell.X
    private lateinit var binding : FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        //private val bot = Computer() создаем позже только при условии что выбран режим компьютера
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }


}