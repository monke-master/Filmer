package ru.monke.filmer.data.shows

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class TopShowRequestBuilder {

    fun build(
        countryCode: String,
        service: String
    ): HttpRequestBuilder {
        return request {
            url.takeFrom(TOP_SHOWS_ENDPOINT)
            parameter(COUNTRY_PARAM, countryCode)
            parameter(SERVICE_PARAM, service)
        }
    }
}