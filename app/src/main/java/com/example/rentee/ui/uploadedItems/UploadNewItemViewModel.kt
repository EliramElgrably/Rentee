package com.example.rentee.ui.uploadedItems

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentee.data.Item
import com.example.rentee.data.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadNewItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _newItem = MutableLiveData<Item>()

    val newItem: LiveData<Item>
        get() = _newItem

    private val _isUploaded = MutableLiveData<Boolean>(false)

    val isUploaded: LiveData<Boolean>
        get() = _isUploaded


    fun uploadItem(bitmap: Bitmap, description: String) {
        val item: Item = Item(" ", null, description, null)
        _newItem.value = item

        launchDataLoad {  itemRepository.insert(bitmap, _newItem.value!!)}
    }

    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                block()
            } finally {
                _isUploaded.value = true
            }
        }
    }
}