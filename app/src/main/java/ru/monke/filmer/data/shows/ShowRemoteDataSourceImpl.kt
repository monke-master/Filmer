package ru.monke.filmer.data.shows

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.monke.filmer.di.ClassProvider
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ShowRemoteDataSourceImpl"

@Singleton
class ShowRemoteDataSourceImpl @Inject constructor(
    private val ktorProvider: ClassProvider<HttpClient>
): ShowRemoteDataSource {

    private val ktor = ktorProvider.get()

    override suspend fun getTopShows(
        countryCode: String,
        service: String
    ): Result<List<ShowRemote>> {
        return try {
            val request = TopShowRequestBuilder().build(
                countryCode = countryCode,
                service = service
            )
            val data: List<ShowRemote> = ktor.get(request).body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getFreshShows(
        countryCode: String,
        year: Int
    ): Result<List<ShowRemote>> {
        return try {
            val request = FilterRequestBuilder().build(
                countryCode = countryCode,
                filters = hashMapOf(
                    MIN_YEAR_PARAM to year.toString(),
                    RATING_PARAM to GOOD_RATING.toString(),
                ),
            )
            val res = ktor.get(request)
            val data: ShowResponse = res.body()
            Result.success(data.shows)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getShowById(id: String): Result<ShowRemote> {
        return try {
            val request = GetShowRequestBuilder().build(id)
            val res = ktor.get(request)
            val data: ShowRemote = res.body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getShowsByFilter(
        countryCode: String,
        filters: HashMap<String, Any>
    ): Result<List<ShowRemote>> {
        return try {
            val request = FilterRequestBuilder().build(
                countryCode = countryCode,
                filters = filters
            )
            val res = ktor.get(request)
            val data: ShowResponse = res.body()
            Result.success(data.shows)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getGenres(): Result<List<GenreRemote>> {
        return try {
            val request = GetGenresRequestBuilder().build()
            val data: List<GenreRemote> = ktor.get(request).body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}