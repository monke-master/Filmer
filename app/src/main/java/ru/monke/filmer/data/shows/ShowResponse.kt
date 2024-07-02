package ru.monke.filmer.data.shows

import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    val shows: List<ShowRemote>,
    val hasMore: Boolean?,
    val nextCursor: String? = null
)