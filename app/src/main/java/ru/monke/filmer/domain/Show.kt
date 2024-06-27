package ru.monke.filmer.domain

data class Show(
    val id: String,
    val title: String,
    val rating: Int,
    val year: Int,
    val duration: Int?,
    val posters: Posters,
    val overview: String,
    val genres: List<Genre>
)
