package ru.monke.filmer.domain

import ru.monke.filmer.data.shows.GENRES_PARAM
import ru.monke.filmer.data.shows.models.PaginationResult
import ru.monke.filmer.data.shows.MIN_RATING_PARAM
import javax.inject.Inject

class GetRecommendedShowsUseCase @Inject constructor(
    private val showsRepository: ShowRepository
) {

    suspend fun execute(
        nextCursor: String? = null,
        genre: Genre
    ): Result<PaginationResult<Show>> {
        val filters = hashMapOf<String, Any>(
            MIN_RATING_PARAM to 10
        )
        if (genre.id != ALL_GENRE.id) filters[GENRES_PARAM] = genre.name
        return showsRepository.getShowsByFiltersWithCursor(
            filters = filters,
            nextCursor = nextCursor)
    }
}