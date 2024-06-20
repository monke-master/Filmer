package ru.monke.filmer.domain

data class Show(
    val title: String,
    val category: String,
    val rating: Int,
    val date: Long = 0,
    val duration: Int = 0
)
