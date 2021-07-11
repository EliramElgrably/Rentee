package com.example.rentee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rentee.NavGraphDirections
import com.example.rentee.databinding.FragmentHomeBinding
import com.example.rentee.ui.signIn.SignInViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val signInViewModel: SignInViewModel by activityViewModels()

    // Firebase instance variables
    //private lateinit var auth: FirebaseAuth

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

        if (signInViewModel.user.value == null) {
            goToSignInPage()
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

    private fun goToSignInPage() {
        val direction =
            NavGraphDirections.actionGlobalFragmentSignIn()
        findNavController().navigate(direction)
    }

}