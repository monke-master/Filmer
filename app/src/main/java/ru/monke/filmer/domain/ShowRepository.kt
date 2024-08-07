package ru.monke.filmer.domain

import ru.monke.filmer.data.shows.models.PaginationResult

interface ShowRepository {

    suspend fun getTopShows(): Result<List<Show>>

    suspend fun getFreshShows(year: Int): Result<List<Show>>

    suspend fun getShowById(id: String): Result<Show>

    suspend fun getShowsByFilters(
        countryCode: String? = null,
        filters: HashMap<String, Any>
    ): Result<List<Show>>

    suspend fun getTodayShow(genre: Genre): Result<Show>

    suspend fun getGenres(): Result<List<Genre>>

    suspend fun getShowsByFiltersWithCursor(
        countryCode: String? = null,
        nextCursor: String?,
        filters: HashMap<String, Any>
    ): Result<PaginationResult<Show>>

    suspend fun searchShows(
        country: String? = null,
        title: String,
        filters: HashMap<String, Any> = hashMapOf()
    ): Result<List<Show>>
}