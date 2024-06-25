package ru.monke.filmer.data

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val w240: String? = null,
    val w360: String? = null,
    val w480: String? = null,
    val w600: String? = null,
    val w720: String? = null,
    val w1080: String? = null,
    val w1440: String? = null,
)
