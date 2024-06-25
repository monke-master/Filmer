package ru.monke.filmer.data

interface ShowRemoteDataSource {

    suspend fun getTopShows(
        countryCode: String = DEFAULT_COUNTRY_VALUE,
        service: String = DEFAULT_SERVICE_VALUE
    ): Result<List<ShowRemote>>

    suspend fun getFreshShows(
        countryCode: String = DEFAULT_COUNTRY_VALUE,
        year: Int
    ): Result<List<ShowRemote>>
}