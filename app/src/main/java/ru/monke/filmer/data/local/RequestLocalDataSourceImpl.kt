package ru.monke.filmer.data.local

import ru.monke.filmer.data.shows.models.ShowRequest
import ru.monke.filmer.data.toDomain
import ru.monke.filmer.data.toRoomEntity
import ru.monke.filmer.domain.Genre
import javax.inject.Inject

class RequestLocalDataSourceImpl @Inject constructor(
    databaseProvider: FilmerDatabaseProvider
): RequestLocalDataSource {

    private val database = databaseProvider.get()

    override suspend fun setLastRequest(showRequest: ShowRequest) {
        database.requestDAO().insertRequest(showRequest.toRoomEntity())
    }

    override suspend fun getLastShowRequest(genre: Genre): ShowRequest? {
        return database.requestDAO().getRequestByGenre(genre.id)?.toDomain()
    }
}