package ru.monke.filmer.domain

interface ShowRepository {

    suspend fun getTopShows(): Result<List<Show>>
}