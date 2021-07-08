package com.example.rentee.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rentee.NavGraphDirections
import com.example.rentee.databinding.FragmentNotificationsBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        binding.btnSignOut.setOnClickListener(View.OnClickListener {
            signOut()
        })

        return binding.root
    }

    private fun signOut() {
        context?.let {
            AuthUI.getInstance().signOut(it).addOnCompleteListener(OnCompleteListener {
                goToHomeFragment()
            })
        }
    }

    private fun goToHomeFragment() {
        val direction =
            NavGraphDirections.actionGlobalFragmentSignIn()
        findNavController().navigate(direction)
    }
}