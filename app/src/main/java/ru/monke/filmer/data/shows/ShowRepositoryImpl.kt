package ru.monke.filmer.data.shows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.monke.filmer.domain.DAY_IN_MILLIS
import ru.monke.filmer.domain.Show
import ru.monke.filmer.domain.ShowRepository
import java.util.Calendar
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val showRemoteDataSource: ShowRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource,
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
                return@withContext Result.success(value.map { it.toDomain() })
            }
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getTodayShow(): Result<Show> {
        return withContext(Dispatchers.IO) {
            val lastRequest = preferencesDataSource.getLastTodayShowRequest()
            lastRequest?.let { request ->
                if (Calendar.getInstance().timeInMillis - request.lastRequestTime < DAY_IN_MILLIS) {
                    return@withContext getShowById(request.showId)
                }
            }
            getTodayShowInternal()
        }
    }

    private suspend fun getTodayShowInternal(): Result<Show> {
        val rating = (70..90).random()
        val result = getShowsByFilters(filters = hashMapOf(RATING_PARAM to rating))
        result.onSuccess { shows ->
            val show = shows[0]
            preferencesDataSource.setLastTodayShowRequest(
                ShowRequest(
                    lastRequestTime = Calendar.getInstance().timeInMillis,
                    showId = show.id
                )
            )
            return Result.success(show)
        }
        return Result.failure(result.exceptionOrNull()!!)
    }
}