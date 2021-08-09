package com.example.finances.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.formatCategory(category: Int): String {
    if (this.length > category) {
        val startIndex = 0
        return "${this.substring(startIndex, category)}..."
    }
    return this
}

fun String.convertDateStringFormatToCalendar(): Calendar {
    val convertDate: Date = SimpleDateFormat("dd/MM/yyyy").parse(this)
    val dateConverted = Calendar.getInstance()
    dateConverted.time = convertDate
    return dateConverted
}