package ru.monke.filmer.data.shows

import kotlinx.serialization.Serializable

@Serializable
data class StreamingOption(
    val service: ServiceRemote,
    val link: String
)