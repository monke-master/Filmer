package ru.monke.filmer.data.shows

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class GetShowRequestBuilder {

    fun build(id: String): HttpRequestBuilder {
        return request {
            url.takeFrom(id)
        }
    }
}