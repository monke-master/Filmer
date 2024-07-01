package ru.monke.filmer.data.shows

import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show

interface ShowRemoteDataSource {

    suspend fun getTopShows(
        countryCode: String = DEFAULT_COUNTRY_VALUE,
        service: String = DEFAULT_SERVICE_VALUE
    ): Result<List<ShowRemote>>

    suspend fun getFreshShows(
        countryCode: String = DEFAULT_COUNTRY_VALUE,
        year: Int
    ): Result<List<ShowRemote>>

    suspend fun getShowById(
        id: String
    ): Result<ShowRemote>

    suspend fun getShowsByFilter(
        countryCode: String,
        filters: HashMap<String, Any>
    ): Result<List<ShowRemote>>

    suspend fun getGenres(): Result<List<GenreRemote>>
}