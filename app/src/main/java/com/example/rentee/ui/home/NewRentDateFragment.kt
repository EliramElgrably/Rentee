package com.example.rentee.ui.home

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rentee.databinding.FragmentNewRentDateBinding
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.core.util.Pair
import androidx.navigation.navGraphViewModels
import com.example.rentee.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import androidx.lifecycle.Observer
import com.example.rentee.data.Rental
import com.example.rentee.utilities.*
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward

class NewRentDateFragment : Fragment(), NewRentalInterface {

    private lateinit var binding: FragmentNewRentDateBinding
    private val newRentViewModel: NewRentViewModel by navGraphViewModels(R.id.navigation_new_rent_address)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRentDateBinding.inflate(inflater, container, false)

        // Bind layout with ViewModel
        binding.viewmodel = newRentViewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        binding.ivClose.setOnClickListener {
            onCloseClicked()
        }

        binding.ivBack.setOnClickListener {
            onBackClicked()
        }

        binding.pickDate.tvDate.setOnClickListener(View.OnClickListener {
            setDateRangePicker()
        })

        binding.backDate.tvDate.setOnClickListener(View.OnClickListener {
            setDateRangePicker()
        })

        binding.pickDate.tvTime.setOnClickListener(View.OnClickListener {
            val hour: Int? = newRentViewModel.newRental.value?.startTime?.hour

            val minutes: Int? = newRentViewModel.newRental.value?.startTime?.minute

            setTimePicker("pickDate", hour, minutes)
        })

        binding.backDate.tvTime.setOnClickListener(View.OnClickListener {
            val hour: Int? = newRentViewModel.newRental.value?.endTime?.hour

            val minutes: Int? = newRentViewModel.newRental.value?.endTime?.minute

            setTimePicker("backDate", hour, minutes)
        })

        newRentViewModel.newRental.observe(viewLifecycleOwner, Observer {
            setDateTimeValues(it)
        })

        return binding.root
    }

    private fun setDateTimeValues(rental: Rental) {
        rental.startDate?.let { binding.pickDate.tvDate.text = formatLDoDateString(it) }
        rental.endDate?.let { binding.backDate.tvDate.text = formatLDoDateString(it) }
        rental.startTime?.let { binding.pickDate.tvTime.text = formatLToTimeString(it) }
        rental.endTime?.let { binding.backDate.tvTime.text = formatLToTimeString(it) }
    }


    private fun setDateRangePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(
                    Pair(
                        (convertLDTToLong(newRentViewModel.newRental.value?.startDateTime)
                            ?: MaterialDatePicker.todayInUtcMilliseconds()),
                        (convertLDTToLong(newRentViewModel.newRental.value?.endDateTime)
                            ?: MaterialDatePicker.todayInUtcMilliseconds() + DAY_IN_MILLI * 2)
                    )
                )
                .build()

        dateRangePicker.show(parentFragmentManager, "new_rental_date_picker")

        dateRangePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click.
            newRentViewModel.setDatesRange(it)
        }
        dateRangePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
        }
        dateRangePicker.addOnCancelListener {
            // Respond to cancel button click.
        }
        dateRangePicker.addOnDismissListener {
            // Respond to dismiss events.
        }
    }

    private fun setTimePicker(viewName: String, hour: Int?, minutes: Int?) {
        val isSystem24Hour = is24HourFormat(context)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(
                    (hour
                        ?: 0)
                )
                .setMinute(
                    (minutes
                        ?: 0)
                )
                .setTitleText("Select time")
                .build()
        picker.show(parentFragmentManager, "new_rental_time_picker")

        picker.addOnPositiveButtonClickListener {
            // call back code
            val pickedHour: Int = picker.hour
            val pickedMinute: Int = picker.minute

            newRentViewModel.setTime(viewName,pickedHour,pickedMinute)
        }
        picker.addOnNegativeButtonClickListener {
            // call back code
        }
        picker.addOnCancelListener {
            // call back code
        }
        picker.addOnDismissListener {
            // call back code
        }
    }

    override fun onCloseClicked() {
        val direction =
            HomeFragmentDirections.actionGlobalNavigationHome()
        findNavController().navigate(direction)
    }

    override fun onBackClicked() {
        findNavController().navigateUp()
    }
}