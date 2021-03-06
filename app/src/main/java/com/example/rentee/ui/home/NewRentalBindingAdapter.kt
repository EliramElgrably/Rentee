package com.example.rentee.ui.home

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rentee.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    } else {
        view.setImageResource(R.drawable.ic_baseline_image_24)
    }
}

@BindingAdapter("isButtonGone")
fun bindIsButtonGone(view: Button, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.visibility = GONE
    } else {
        view.visibility = VISIBLE
    }
}
