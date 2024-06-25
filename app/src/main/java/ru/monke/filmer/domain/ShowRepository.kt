package ru.monke.filmer.domain

interface ShowRepository {

    suspend fun getTopShows(): Result<List<Show>>

    suspend fun getFreshShows(year: Int): Result<List<Show>>

    suspend fun getShowById(id: String): Result<Show>
}