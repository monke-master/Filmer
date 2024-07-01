package ru.monke.filmer.data.shows

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class GetGenresRequestBuilder {

    fun build(): HttpRequestBuilder {
        return request {
            url.takeFrom(GENRES_ENDPOINT)
        }
    }
}