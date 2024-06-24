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

    override suspend fun getTopShows(): Result<List<ShowRemote>> {
        return try {
            val data: List<ShowRemote> =
                ktorProvider.get().get(TopShowRequestBuilder().build()).body()
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}