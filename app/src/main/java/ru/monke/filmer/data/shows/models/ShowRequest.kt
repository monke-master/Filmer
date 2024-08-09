package ru.monke.filmer.data.shows.models

data class ShowRequest(
    val lastRequestTime: Long,
    val genreId: String,
    val showId: String
)
