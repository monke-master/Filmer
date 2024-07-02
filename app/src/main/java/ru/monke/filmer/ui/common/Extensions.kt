package ru.monke.filmer.ui.common

import ru.monke.filmer.domain.Show
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


fun Show.getGenre(): String {
    if (genres.size > 0) {
        return genres[0].name
    }
    return ""
}
