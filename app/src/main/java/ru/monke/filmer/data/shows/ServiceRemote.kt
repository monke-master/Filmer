package ru.monke.filmer.data.shows

import kotlinx.serialization.Serializable

@Serializable
data class ServiceRemote(
    val id: String,
    val name: String,
    val imageSet: ServiceImageSetRemote
)