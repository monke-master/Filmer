package ru.monke.filmer.data.local

import ru.monke.filmer.data.shows.models.ShowRequest
import ru.monke.filmer.domain.Genre

interface RequestLocalDataSource {

    suspend fun setLastRequest(showRequest: ShowRequest)

    suspend fun getLastShowRequest(genre: Genre): ShowRequest?
}