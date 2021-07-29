package com.example.rentee.ui.signIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rentee.R
import com.example.rentee.databinding.FragmentSignInBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val signInViewModel: SignInViewModel by activityViewModels()
    private val signIn: ActivityResultLauncher<Intent> =
        registerForActivityResult(FirebaseAuthUIActivityResultContract(), this::onSignInResult)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)


        // If there is no signed in user, launch FirebaseUI
        // Otherwise head to MainActivity
        if (signInViewModel.user.value == null) {
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                    )
                )
                .build()

            signIn.launch(signInIntent)

            signInViewModel.user.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    goToHomeFragment()
                }
            })
        } else {
            goToHomeFragment()
        }

        return binding.root
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            Log.d(TAG, "Sign in successful!")

            signInViewModel.setUserInfo()

        } else {
            Toast.makeText(
                context,
                "There was an error signing in",
                Toast.LENGTH_LONG
            ).show()

            val response = result.idpResponse
            if (response == null) {
                Log.w(TAG, "Sign in canceled")
            } else {
                Log.w(TAG, "Sign in error", response.error)
            }

            goToHomeFragment()
        }
    }

    private fun goToHomeFragment() {
        val direction =
            SignInFragmentDirections.actionFragmentSignInToHome()
        findNavController().navigate(direction)
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}