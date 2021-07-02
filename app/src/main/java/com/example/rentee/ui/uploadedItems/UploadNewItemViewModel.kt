package com.example.rentee.ui.uploadedItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentee.data.Item

class UploadNewItemViewModel : ViewModel() {

    private val _newItem = MutableLiveData<Item>()

    val newItem: LiveData<Item>
        get() = _newItem


}