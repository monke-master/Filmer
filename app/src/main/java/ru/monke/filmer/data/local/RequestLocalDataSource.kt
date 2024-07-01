package ru.monke.filmer.data.local

import ru.monke.filmer.data.shows.ShowRequest
import ru.monke.filmer.domain.Genre

interface RequestLocalDataSource {

    suspend fun setLastRequest(showRequest: ShowRequest)

    suspend fun getLastShowRequest(genre: Genre): ShowRequest?
}