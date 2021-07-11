package com.example.rentee.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentee.data.User
import com.example.rentee.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var user: LiveData<User?> = userRepository.user.asLiveData()

    init {
        viewModelScope.launch {
            try {
                userRepository.getUserCheck()
            } finally {
                //Todo: add logic
            }
        }
    }

    fun signOut() {
        //Updating the repository
        launchDataLoad { userRepository.deleteUser() }
    }

    fun setUserInfo() {
        //Updating the repository
        launchDataLoad { userRepository.insertCurrentUser() }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                block()
            } finally {
                //_isUploaded.value = true
            }
        }
    }
}