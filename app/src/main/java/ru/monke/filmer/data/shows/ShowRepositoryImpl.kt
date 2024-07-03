package ru.monke.filmer.data.shows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.monke.filmer.data.local.RequestLocalDataSource
import ru.monke.filmer.data.toDomain
import ru.monke.filmer.data.toPaginationResult
import ru.monke.filmer.domain.ALL_GENRE
import ru.monke.filmer.domain.DAY_IN_MILLIS
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show
import ru.monke.filmer.domain.ShowRepository
import java.util.Calendar
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val showRemoteDataSource: ShowRemoteDataSource,
    private val localDataSource: RequestLocalDataSource
): ShowRepository {

    override suspend fun getTopShows(): Result<List<Show>> {
        return withContext(Dispatchers.IO) {
            val result = showRemoteDataSource.getTopShows()
            result.onSuccess { value ->
                return@withContext Result.success(value.map { it.toDomain() })
            }
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getFreshShows(year: Int): Result<List<Show>> {
        return withContext(Dispatchers.IO) {
            val result = showRemoteDataSource.getFreshShows(year = 2024)
            result.onSuccess { value ->
                return@withContext Result.success(value.map { it.toDomain() })
            }
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getShowById(id: String): Result<Show> {
        return withContext(Dispatchers.IO) {
            val result = showRemoteDataSource.getShowById(id)
            result.onSuccess { value ->
                return@withContext Result.success(value.toDomain())
            }
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getShowsByFilters(
        countryCode: String?,
        filters: HashMap<String, Any>
    ): Result<List<Show>> {
        return withContext(Dispatchers.IO) {
            val result = showRemoteDataSource.getShowsByFilter(
                countryCode = countryCode ?: DEFAULT_COUNTRY_VALUE,
                filters = filters
            )
            result.onSuccess { value ->
                return@withContext Result.success(value.shows.map { it.toDomain() })
            }
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getTodayShow(genre: Genre): Result<Show> {
        return withContext(Dispatchers.IO) {
            val lastRequest = localDataSource.getLastShowRequest(genre)
            lastRequest?.let { request ->
                if (Calendar.getInstance().timeInMillis - request.lastRequestTime < DAY_IN_MILLIS) {
                    return@withContext getShowById(request.showId)
                }
            }
            getTodayShowInternal(genre)
        }
    }

    private suspend fun getTodayShowInternal(genre: Genre): Result<Show> {
        val rating = (0..90).random()
        val filters = hashMapOf<String, Any>(MIN_RATING_PARAM to rating)
        if (genre.id != ALL_GENRE.id) filters[GENRES_PARAM] = genre.name

        val result = getShowsByFilters(filters = filters)
        result.onSuccess { shows ->
            val show = shows[0]
            localDataSource.setLastRequest(
                ShowRequest(
                    lastRequestTime = Calendar.getInstance().timeInMillis,
                    showId = show.id,
                    genreId = genre.id
                )
            )
            return Result.success(show)
        }
        return Result.failure(result.exceptionOrNull()!!)
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return withContext(Dispatchers.IO) {
            val result = showRemoteDataSource.getGenres()
            result.onSuccess { list ->
                return@withContext Result.success(list.map { it.toDomain() })
            }
            return@withContext Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getShowsByFiltersWithCursor(
        countryCode: String?,
        nextCursor: String?,
        filters: HashMap<String, Any>,
    ): Result<PaginationResult<Show>> {
        return withContext(Dispatchers.IO) {
            nextCursor?.let { filters[CURSOR_PARAM] = nextCursor }
            val result = showRemoteDataSource.getShowsByFilter(
                countryCode = countryCode ?: DEFAULT_COUNTRY_VALUE,
                filters = filters
            )
            result.onSuccess { value ->
                return@withContext Result.success(value.toPaginationResult())
            }
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun searchShows(
        country: String?,
        title: String,
        filters: HashMap<String, Any>
    ): Result<List<Show>> {
        return withContext(Dispatchers.IO) {
            val shows = showRemoteDataSource.searchShows(
                country = country ?: DEFAULT_COUNTRY_VALUE,
                title = title,
                filters = filters
            ).getOrElse {
                return@withContext Result.failure(it)
            }
            return@withContext Result.success(shows.map { it.toDomain() })
        }
    }

}