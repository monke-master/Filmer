package ru.monke.filmer.domain

interface ShowRepository {

    suspend fun getTopShows(): Result<List<Show>>

    suspend fun getFreshShows(year: Int): Result<List<Show>>

    suspend fun getShowById(id: String): Result<Show>

    suspend fun getShowsByFilters(
        countryCode: String? = null,
        filters: HashMap<String, Any>
    ): Result<List<Show>>

    suspend fun getTodayShow(): Result<Show>

    suspend fun getGenres(): Result<List<Genre>>
}