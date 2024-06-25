package ru.monke.filmer.data.shows

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request

class GetShowRequestBuilder {

    fun build(id: String): HttpRequestBuilder {
        return request {
            parameter(ID_PARAM, id)
        }
    }
}