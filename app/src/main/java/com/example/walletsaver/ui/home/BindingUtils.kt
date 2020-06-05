package com.example.walletsaver.ui.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.walletsaver.R

@BindingAdapter("typeImage")
fun ImageView.setTypeImage(index: Int?) {
    index?.let {
        setImageResource(when (index) {
            1 -> R.drawable.ic_home
            2 -> R.drawable.ic_laptop_mac
            3 -> R.drawable.ic_directions_bus
            4 -> R.drawable.ic_grain
            5 -> R.drawable.ic_ac_unit
            6 -> R.drawable.ic_videogame
            else -> R.drawable.ic_pie_chart
        })
    }
}