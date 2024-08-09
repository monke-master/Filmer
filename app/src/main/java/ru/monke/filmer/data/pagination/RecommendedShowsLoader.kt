package ru.monke.filmer.data.pagination

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.monke.filmer.data.shows.models.PaginationResult
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.GetRecommendedShowsUseCase
import ru.monke.filmer.domain.Show

class RecommendedShowsLoader @AssistedInject constructor (
    private val getRecommendedShowsUseCase: GetRecommendedShowsUseCase,
    @Assisted private val genre: Genre
): PaginationLoader {

    override suspend fun loadNext(cursor: String?): Result<PaginationResult<Show>> {
        return getRecommendedShowsUseCase.execute(nextCursor = cursor, genre = genre)
    }

    @AssistedFactory
    interface Factory {
        fun create(genre: Genre): RecommendedShowsLoader
    }

}