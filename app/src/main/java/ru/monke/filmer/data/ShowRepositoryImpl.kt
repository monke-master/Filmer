package ru.monke.filmer.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.monke.filmer.domain.Show
import ru.monke.filmer.domain.ShowRepository
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val showRemoteDataSource: ShowRemoteDataSource
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

}