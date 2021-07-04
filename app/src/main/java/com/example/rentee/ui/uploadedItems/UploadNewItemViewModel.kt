package com.example.rentee.ui.uploadedItems

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


    fun uploadItem(description: String) {
        val item: Item = Item(0, null, description)
        _newItem.value = item

        viewModelScope.launch {
            itemRepository.insert(_newItem.value!!)
        }
    }
}