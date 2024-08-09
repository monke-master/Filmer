package ru.monke.filmer.data.shows

import ru.monke.filmer.data.shows.models.GenreRemote
import ru.monke.filmer.data.shows.models.ShowRemote
import ru.monke.filmer.data.shows.models.ShowResponse

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
    ): Result<ShowResponse>

    suspend fun getGenres(): Result<List<GenreRemote>>

    suspend fun searchShows(
        country: String,
        title: String,
        filters: HashMap<String, Any>
    ): Result<List<ShowRemote>>
}