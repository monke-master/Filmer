package ru.monke.filmer.data

import kotlinx.serialization.Serializable

@Serializable
data class ShowRemote(
    val id: String,
    val title: String,
    val rating: Int,
    val firstAirYear: Int? = null,
    val lastAirYear: Int? = null,
    val releaseYear: Int? = null,
    val overview: String,
    val runtime: Int? = null,
    val imageSet: ImageSet
)