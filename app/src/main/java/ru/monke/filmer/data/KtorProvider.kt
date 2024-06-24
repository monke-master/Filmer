package ru.monke.filmer.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Provider


class KtorProvider @Inject constructor(): ClassProvider<HttpClient> {

    override fun get(): HttpClient {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url(BASE_URl)
                header(RAPID_API_KEY, API_KEY)
            }
        }
        return client
    }
}