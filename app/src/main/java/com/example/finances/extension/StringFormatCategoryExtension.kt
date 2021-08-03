package com.example.finances.extension

fun String.formatCategory(category: Int): String {
    if (this.length > category) {
        val startIndex = 0
        return "${this.substring(startIndex, category)}..."
    }
    return this
}