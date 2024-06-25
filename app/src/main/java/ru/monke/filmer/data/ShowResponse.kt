package ru.monke.filmer.data

import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    val shows: List<ShowRemote>
)