package com.example.rentee.ui.uploadedItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentee.data.Item
import com.example.rentee.data.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadedItemsListViewModel @Inject internal constructor(
    itemsRepository: ItemRepository
): ViewModel() {
    val itemsList: LiveData<List<Item>> =
        itemsRepository.allItems.asLiveData()

    init {
        viewModelScope.launch {
            try {
                itemsRepository.refreshItemsList()
            } finally {
                //_isUploaded.value = true
            }
        }
    }
}