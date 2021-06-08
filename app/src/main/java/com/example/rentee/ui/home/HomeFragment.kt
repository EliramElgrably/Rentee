package com.example.rentee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rentee.R
import com.example.rentee.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Bind layout with ViewModel
        binding.viewmodel = homeViewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        binding.btnStartOrder.setOnClickListener(View.OnClickListener {
            val direction =
                HomeFragmentDirections.actionNavigationHomeToNavigationNewRental()
            findNavController().navigate(direction)
        })

        //binding.textHome.text = "This is home Fragment"
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = it
//        })
        return binding.root
    }

}