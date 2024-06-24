package ru.monke.filmer.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ShowRemoteDataSourceImpl: ShowRemoteDataSource {


    val TAG = "ShowRemoteDataSourceImpl"

    suspend fun getTopShows() {
        val data: List<ShowRemote> =
            getClient().get(TopShowRequestBuilder().build()).body()
        Log.d(TAG, "getTopShows: ${data}")
    }


    fun getClient(): HttpClient {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url(BASE_URl)
                header(RAPID_API_KEY, API_KEY)
            }
        }
        return client
    }


}