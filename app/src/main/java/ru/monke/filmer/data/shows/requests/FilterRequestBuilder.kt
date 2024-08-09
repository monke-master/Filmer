package ru.monke.filmer.data.shows.requests

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom
import ru.monke.filmer.data.shows.ApiKeySetter
import ru.monke.filmer.data.shows.COUNTRY_PARAM
import ru.monke.filmer.data.shows.FILTERS_ENDPOINT
import ru.monke.filmer.data.shows.RAPID_API_KEY

class FilterRequestBuilder {

    fun build(
        apiKey: String,
        countryCode: String,
        filters: HashMap<String, Any>
    ): HttpRequestBuilder {
        return request {
            header(RAPID_API_KEY, apiKey)
            url.takeFrom(FILTERS_ENDPOINT)
            parameter(COUNTRY_PARAM, countryCode)

            filters.forEach { (param, value) ->
                parameter(param, value)
            }
        }
    }
}