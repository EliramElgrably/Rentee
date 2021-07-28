package com.example.rentee.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rentee.NavGraphDirections
import com.example.rentee.databinding.FragmentNotificationsBinding
import com.example.rentee.ui.signIn.SignInViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener

class NotificationsFragment : Fragment() {

    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val signInViewModel: SignInViewModel by activityViewModels()
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        binding.btnSignOut.setOnClickListener(View.OnClickListener {
            signOut()
        })

        signInViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                goToHomeFragment()
            }
        })
        return binding.root
    }

    private fun signOut() {
        context?.let {
            AuthUI.getInstance().signOut(it).addOnCompleteListener(OnCompleteListener {

                signInViewModel.signOut()
            })
        }
    }

    private fun goToHomeFragment() {
        val direction =
            NavGraphDirections.actionGlobalFragmentSignIn()
        findNavController().navigate(direction)
    }
}