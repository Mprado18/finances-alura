package com.example.finances.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatDate(): String {
    val dateFormat = "dd/MM/yyyy"
    val format = SimpleDateFormat(dateFormat)

    return format.format(time)
}