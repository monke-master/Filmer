package ru.monke.filmer.data.shows.models

import kotlinx.serialization.Serializable

@Serializable
data class StreamingOption(
    val service: ServiceRemote,
    val link: String
)