package com.example.rentee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rentee.databinding.FragmentNewRentBinding

class NewRentFragment : Fragment()  {

    private lateinit var binding: FragmentNewRentBinding
    private val newRentViewModel: NewRentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRentBinding.inflate(inflater, container, false)

        // Bind layout with ViewModel
        binding.viewmodel = newRentViewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }
}