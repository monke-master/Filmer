package ru.monke.filmer.data.shows.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageSet(
    val verticalPoster: Image,
    val horizontalPoster: Image,
)
