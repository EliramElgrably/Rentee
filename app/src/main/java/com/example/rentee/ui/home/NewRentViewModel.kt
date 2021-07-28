package com.example.rentee.ui.home

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentee.data.ItemRepository
import com.example.rentee.data.Rental
import com.example.rentee.data.UserRepository
import com.example.rentee.utilities.convertLongToLT
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NewRentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _newRental = MutableLiveData<Rental>()

    private val _isSelectItemsGone = MutableLiveData<Boolean>()

    val newRental: LiveData<Rental>
        get() = _newRental

    val isSelectItemsGone: LiveData<Boolean>
        get() = _isSelectItemsGone

    fun setAddress(address: String) {
        val rental: Rental = Rental(
            0, null, address, null, null, null, null,
            null, null
        )
        _newRental.value = rental
        //TODO: find a better way to handle it
        _isSelectItemsGone.value = true
    }

    fun setDatesRange(dates: Pair<Long, Long>) {
        val localDateStart: LocalDate = convertLongToLT(dates.first)
        val localDateEnd: LocalDate = convertLongToLT(dates.second)

        _newRental.value = Rental(
            0, userRepository.user.value?.userId, newRental.value?.address,
            null, null, localDateStart, localDateEnd,
            newRental.value?.startTime, newRental.value?.endTime
        )

        setIsDatesValid()
    }

    fun setTime(viewName: String, hour: Int, minutes: Int) {
        if (viewName.equals("pickDate")) {

            val startTime: LocalTime = LocalTime.of(hour, minutes)

            _newRental.value = Rental(
                0, null, newRental.value?.address,
                null, null, newRental.value?.startDate, newRental.value?.endDate,
                startTime, newRental.value?.endTime
            )
        } else {
            val endTime: LocalTime = LocalTime.of(hour, minutes)

            _newRental.value = Rental(
                0, null, newRental.value?.address,
                null, null, newRental.value?.startDate, newRental.value?.endDate,
                newRental.value?.startTime, endTime
            )
        }

        setIsDatesValid()
    }

    private fun setIsDatesValid() {
        newRental.value?.let {
            if (it.startDate != null &&
                it.endDate != null &&
                it.startTime != null &&
                it.endTime != null
            ) {
                _isSelectItemsGone.value = false
            }
        }
    }
}