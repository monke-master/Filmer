package ru.monke.filmer.data.shows.requests

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.takeFrom
import ru.monke.filmer.data.shows.ApiKeySetter
import ru.monke.filmer.data.shows.RAPID_API_KEY
import ru.monke.filmer.data.shows.SHOWS_ENDPOINT

class GetShowRequestBuilder {

    fun build(
        apiKey: String,
        id: String
    ): HttpRequestBuilder {
        return request {
            header(RAPID_API_KEY, apiKey)
            url.takeFrom("$SHOWS_ENDPOINT/$id")
        }
    }
}