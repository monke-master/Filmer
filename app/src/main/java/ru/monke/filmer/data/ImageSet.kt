package ru.monke.filmer.data

import kotlinx.serialization.Serializable

@Serializable
data class ImageSet(
    val verticalPoster: Image,
    val horizontalPoster: Image,
)
