package ru.monke.filmer.data.shows.models

import kotlinx.serialization.Serializable

@Serializable
data class ServiceImageSetRemote(
    val lightThemeImage: String,
    val darkThemeImage: String,
    val whiteImage: String
)