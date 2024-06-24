package ru.monke.filmer.data

interface ShowRemoteDataSource {

    suspend fun getTopShows(): Result<List<ShowRemote>>
}