package com.example.rentee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.rentee.R
import com.example.rentee.databinding.FragmentNewRentAddressBinding
import com.example.rentee.databinding.FragmentNewRentBinding

class NewRentAddressFragment : Fragment() {

    private lateinit var binding: FragmentNewRentAddressBinding
    private val newRentViewModel: NewRentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRentAddressBinding.inflate(inflater, container, false)

        // Bind layout with ViewModel
        binding.viewmodel = newRentViewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        binding.ivClose.setOnClickListener {
            navigateToHomePage()
        }

        return binding.root
    }

    private fun navigateToHomePage() {
       findNavController().navigateUp()
    }
}