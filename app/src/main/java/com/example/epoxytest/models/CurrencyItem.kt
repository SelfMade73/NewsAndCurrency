package com.example.epoxytest.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class CurrencyItem(
    @PrimaryKey
    val name : String,
    val price : Double,
    val date : Long? = null,
    val difference : Double? = null
): Parcelable

