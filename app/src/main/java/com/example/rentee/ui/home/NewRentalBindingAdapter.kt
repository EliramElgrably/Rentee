package com.example.rentee.ui.home

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.databinding.BindingAdapter


@BindingAdapter("isButtonGone")
fun bindIsButtonGone(view: Button, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.visibility = GONE
    } else {
        view.visibility = VISIBLE
    }
}
