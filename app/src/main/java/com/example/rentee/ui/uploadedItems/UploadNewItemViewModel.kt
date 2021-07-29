package com.example.rentee.ui.uploadedItems

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentee.data.Item
import com.example.rentee.data.ItemRepository
import com.example.rentee.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadNewItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _newItem = MutableLiveData<Item>()

    val newItem: LiveData<Item>
        get() = _newItem

    private val _isUploaded = MutableLiveData<Boolean>(false)

    val isUploaded: LiveData<Boolean>
        get() = _isUploaded


    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner



    fun uploadItem(bitmap: Bitmap, description: String) {
        userRepository.user.value?.let {
            val item: Item = Item(" ", it.userId, description, null, null)
            _newItem.value = item

            launchDataLoad {  itemRepository.insert(bitmap, _newItem.value!!)}
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } finally {
                _spinner.value = false
                _isUploaded.value = true
            }
        }
    }
}