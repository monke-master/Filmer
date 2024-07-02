package ru.monke.filmer.data.pagination

import ru.monke.filmer.data.shows.PaginationResult
import ru.monke.filmer.domain.Show

interface PaginationLoader {

    suspend fun loadNext(cursor: String?): Result<PaginationResult<Show>>
}