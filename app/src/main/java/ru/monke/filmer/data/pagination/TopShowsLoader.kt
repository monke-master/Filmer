package ru.monke.filmer.data.pagination

import ru.monke.filmer.data.shows.PaginationResult
import ru.monke.filmer.domain.GetTopShowsUseCase
import ru.monke.filmer.domain.Show
import javax.inject.Inject

class TopShowsLoader @Inject constructor(
    private val getTopShowsUseCase: GetTopShowsUseCase
): PaginationLoader {

    override suspend fun loadNext(cursor: String?): Result<PaginationResult<Show>> {
        val shows = getTopShowsUseCase.execute().getOrElse {
            return Result.failure(it)
        }
        return Result.success(PaginationResult(items = shows, nextKey = null))
    }
}