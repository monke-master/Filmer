package ru.monke.filmer.data.shows.requests

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom
import ru.monke.filmer.data.shows.ApiKeySetter
import ru.monke.filmer.data.shows.COUNTRY_PARAM
import ru.monke.filmer.data.shows.RAPID_API_KEY
import ru.monke.filmer.data.shows.SERVICE_PARAM
import ru.monke.filmer.data.shows.TOP_SHOWS_ENDPOINT

class TopShowRequestBuilder {

    fun build(
        apiKey: String,
        countryCode: String,
        service: String
    ): HttpRequestBuilder {
        return request {
            header(RAPID_API_KEY, apiKey)
            url.takeFrom(TOP_SHOWS_ENDPOINT)
            parameter(COUNTRY_PARAM, countryCode)
            parameter(SERVICE_PARAM, service)
        }
    }
}