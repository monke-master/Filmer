package ru.monke.filmer.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Provider

private const val TAG = "ShowRemoteDataSourceImpl"

class ShowRemoteDataSourceImpl @Inject constructor(
    private val ktorProvider: ClassProvider<HttpClient>
): ShowRemoteDataSource {

    override suspend fun getTopShows(
        countryCode: String,
        service: String
    ): Result<List<ShowRemote>> {
        return try {
            val request = TopShowRequestBuilder().build(
                countryCode = countryCode,
                service = service
            )
            val data: List<ShowRemote> =
                ktorProvider.get().get(request).body()
            Result.success(data)
        } catch (e: Exception) {
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
                filters = hashMapOf(MIN_YEAR_PARAM to year.toString())
            )
            val res =  ktorProvider.get().get(request)
            val data: ShowResponse = res.body()
            Result.success(data.shows)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}