package ru.monke.filmer.data.shows

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.takeFrom

class SearchShowsRequestBuilder {

    fun build(
        country: String,
        query: String,
        filters: HashMap<String, Any>
    ): HttpRequestBuilder {
        return request {
            url.takeFrom(TITLE_ENDPOINT)
            parameter(COUNTRY_PARAM, country)
            parameter(TITLE_PARAM, query)
            filters.forEach { (param, value) -> parameter(param, value)}
        }
    }
}