package com.example.epoxytest

import java.text.SimpleDateFormat
import java.util.*

fun dateByDaysAgo(daysAgo: Int) : Date{
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
    return calendar.time
}

fun Date.toRequiredFormat() : String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)
