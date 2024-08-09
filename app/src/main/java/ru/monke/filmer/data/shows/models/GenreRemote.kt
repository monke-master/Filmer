package ru.monke.filmer.data.shows.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreRemote(
    val id: String,
    val name: String,
)