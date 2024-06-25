package ru.monke.filmer.domain

data class Show(
    val title: String,
    val category: String,
    val rating: Int,
    val year: Int,
    val duration: Int?,
    val posters: Posters,
    val overview: String = ""
)
