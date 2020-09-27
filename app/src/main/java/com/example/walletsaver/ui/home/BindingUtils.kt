package com.example.walletsaver.ui.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.walletsaver.R

@BindingAdapter("typeImage")
fun ImageView.setTypeImage(index: Int?) {
    index?.let {
        setImageResource(when (index) {
            1 -> R.drawable.ic_baseline_radio_button_unchecked_urgent
            2 -> R.drawable.ic_baseline_radio_button_unchecked_high
            3 -> R.drawable.ic_baseline_radio_button_unchecked_medium
            4 -> R.drawable.ic_baseline_radio_button_unchecked_low
            5 -> R.drawable.ic_baseline_check_circle_outline
            else -> R.drawable.ic_baseline_radio_button_unchecked
        })
    }
}