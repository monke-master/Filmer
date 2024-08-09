package ru.monke.filmer.data.shows

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.monke.filmer.data.shows.models.GenreRemote
import ru.monke.filmer.data.shows.models.ShowRemote
import ru.monke.filmer.data.shows.models.ShowResponse
import ru.monke.filmer.data.shows.requests.FilterRequestBuilder
import ru.monke.filmer.data.shows.requests.GetGenresRequestBuilder
import ru.monke.filmer.data.shows.requests.GetShowRequestBuilder
import ru.monke.filmer.data.shows.requests.SearchShowsRequestBuilder
import ru.monke.filmer.data.shows.requests.TopShowRequestBuilder
import ru.monke.filmer.di.ClassProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ShowRemoteDataSourceImpl"

@Singleton
class ShowRemoteDataSourceImpl @Inject constructor(
    ktorProvider: ClassProvider<HttpClient>,
    private val requestExecutor: RequestExecutor
): ShowRemoteDataSource {

    private val ktor = ktorProvider.get()

    override suspend fun getTopShows(
        countryCode: String,
        service: String
    ): Result<List<ShowRemote>> {
        return try {
            val res = requestExecutor.executeRequest { apiKey ->
                val request = TopShowRequestBuilder().build(
                    countryCode = countryCode,
                    service = service,
                    apiKey = apiKey
                )
                ktor.get(request)
            }
            val data: List<ShowRemote> = res.body()
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
            val res = requestExecutor.executeRequest { apiKey ->
                val request = FilterRequestBuilder().build(
                    countryCode = countryCode,
                    filters = hashMapOf(
                        MIN_YEAR_PARAM to year.toString(),
                        MIN_RATING_PARAM to GOOD_RATING.toString(),
                    ),
                    apiKey = apiKey
                )
                ktor.get(request)
            }
            val data: ShowResponse = res.body()
            Result.success(data.shows)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getShowById(id: String): Result<ShowRemote> {
        return try {
            val res = requestExecutor.executeRequest { apiKey ->
                val request = GetShowRequestBuilder().build(apiKey, id)
                ktor.get(request)
            }
            val data: ShowRemote = res.body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getShowsByFilter(
        countryCode: String,
        filters: HashMap<String, Any>,
    ): Result<ShowResponse> {
        return try {
            val res = requestExecutor.executeRequest { apiKey ->
                val request = FilterRequestBuilder().build(
                    apiKey = apiKey,
                    countryCode = countryCode,
                    filters = filters
                )
                ktor.get(request)
            }
            val data: ShowResponse = res.body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getGenres(): Result<List<GenreRemote>> {
        return try {
            val res = requestExecutor.executeRequest { apiKey ->
                val request =GetGenresRequestBuilder().build(apiKey)
                ktor.get(request)
            }
            val data: List<GenreRemote> = res.body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun searchShows(
        country: String,
        title: String,
        filters: HashMap<String, Any>
    ): Result<List<ShowRemote>> {
        return try {
            val res = requestExecutor.executeRequest { apiKey ->
                val request = SearchShowsRequestBuilder().build(
                    country = country,
                    query = title,
                    filters = filters,
                    apiKey = apiKey
                )
                ktor.get(request)
            }
            val data: List<ShowRemote> = res.body()
            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}