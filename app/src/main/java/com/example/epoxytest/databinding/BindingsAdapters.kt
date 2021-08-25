package com.example.epoxytest.databinding

import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import com.example.epoxytest.R
import com.example.epoxytest.models.Category
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso

@BindingAdapter("insert")
fun addChips(view: ChipGroup, categories:  List<Category>) {
    view.removeAllViews()
    categories.forEach { category ->
        val chip = Chip(view.context,null, R.style.Widget_MaterialComponents_Chip_Choice)
        chip.text = category.category
        chip.isClickable = true
        chip.isFocusable = true
        chip.isCheckable = true
        chip.isChecked = category.checked
        chip.chipBackgroundColor = AppCompatResources.getColorStateList(view.context, R.color.chips)
        view.addView(chip)
    }
}

@BindingAdapter("onCheckedChangeListener")
fun setOnCheckChange( view: ChipGroup, listener: View.OnLayoutChangeListener){
    view.addOnLayoutChangeListener(listener)
}


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let{ url ->
        Picasso.get()
            .load(url)
            .into(view)
    }
}

