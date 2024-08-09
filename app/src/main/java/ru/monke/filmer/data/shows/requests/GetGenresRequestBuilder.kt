package ru.monke.filmer.data.shows.requests

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.takeFrom
import ru.monke.filmer.data.shows.ApiKeySetter
import ru.monke.filmer.data.shows.GENRES_ENDPOINT
import ru.monke.filmer.data.shows.RAPID_API_KEY

class GetGenresRequestBuilder {

    fun build(
        apiKey: String
    ): HttpRequestBuilder {
        return request {
            header(RAPID_API_KEY, apiKey)
            url.takeFrom(GENRES_ENDPOINT)
        }
    }
}