package ru.monke.filmer.data.shows

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class FilterRequestBuilder {

    fun build(
        countryCode: String,
        filters: HashMap<String, String>
    ): HttpRequestBuilder {
        return request {
            url.takeFrom(FILTERS_ENDPOINT)
            parameter(COUNTRY_PARAM, countryCode)

            filters.forEach { (param, value) ->
                parameter(param, value)
            }
        }
    }
}