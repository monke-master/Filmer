package ru.monke.filmer.ui.common

import java.util.ArrayList
import java.util.Calendar

fun Int.getRating() = (this / 10f).toString()

fun<T> List<T>.repeat(n: Int): List<T> {
    val newList = ArrayList<T>()
    for (i in 1..n) {
        newList.addAll(this)
    }
    return newList
}


fun Long.getYear(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.YEAR)
}