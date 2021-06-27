package com.example.rentee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.rentee.R
import com.example.rentee.databinding.FragmentNewRentAddressBinding

class NewRentalAddressFragment : Fragment(), NewRentalInterface {

    private lateinit var binding: FragmentNewRentAddressBinding
    private val newRentViewModel: NewRentViewModel by navGraphViewModels(R.id.navigation_new_rent_address)

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
            onCloseClicked()
        }

        binding.ivBack.setOnClickListener {
            onBackClicked()
        }

        binding.btnDate.setOnClickListener {
            if (binding.etAddress.text.isNotEmpty()) {
                newRentViewModel.setAddress(binding.etAddress.text.toString())
            }
            navigateToDatePage()
        }

        return binding.root
    }

    override fun onCloseClicked() {
        val direction =
            HomeFragmentDirections.actionGlobalNavigationHome()
        findNavController().navigate(direction)
    }

    override fun onBackClicked() {
        findNavController().navigateUp()
    }

    private fun navigateToDatePage() {
        val direction =
            NewRentalAddressFragmentDirections.actionNavigationNewRentAddressToNavigationNewRentDate()
        findNavController().navigate(direction)
    }
}