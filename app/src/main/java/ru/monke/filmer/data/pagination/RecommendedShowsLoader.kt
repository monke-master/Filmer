package ru.monke.filmer.data.pagination

import ru.monke.filmer.data.shows.PaginationResult
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.GetRecommendedShowsUseCase
import ru.monke.filmer.domain.Show
import javax.inject.Inject

class RecommendedShowsLoader (
    private val getRecommendedShowsUseCase: GetRecommendedShowsUseCase,
    private val genre: Genre
): PaginationLoader {

    override suspend fun loadNext(cursor: String?): Result<PaginationResult<Show>> {
        return getRecommendedShowsUseCase.execute(nextCursor = cursor, genre = genre)
    }

}