package com.example.rentee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rentee.NavGraphDirections
import com.example.rentee.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth

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

        // Initialize Firebase Auth and check if the user is signed in
        auth = Firebase.auth
        if (auth.currentUser == null) {
            val direction =
                NavGraphDirections.actionGlobalFragmentSignIn()
            findNavController().navigate(direction)
        }

        binding.btnStartOrder.setOnClickListener(View.OnClickListener {
            val direction =
                HomeFragmentDirections.actionNavigationHomeToNavigationNewRentAddress()
            findNavController().navigate(direction)
        })

        //binding.textHome.text = "This is home Fragment"
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = it
//        })
        return binding.root
    }

}