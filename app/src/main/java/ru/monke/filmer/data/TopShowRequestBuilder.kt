package ru.monke.filmer.data

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class TopShowRequestBuilder {

    fun build(): HttpRequestBuilder {
        return request {
            url.takeFrom("top")
            parameter(COUNTRY_PARAM, "us")
            parameter(SERVICE_PARAM, "netflix")
        }
    }
}