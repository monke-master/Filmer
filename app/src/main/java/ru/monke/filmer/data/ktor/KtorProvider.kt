package ru.monke.filmer.data.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.monke.filmer.data.shows.API_KEY
import ru.monke.filmer.data.shows.BASE_URl
import ru.monke.filmer.di.ClassProvider
import ru.monke.filmer.data.shows.RAPID_API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorProvider @Inject constructor(): ClassProvider<HttpClient> {

    private var client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url(BASE_URl)
        }
    }

    override fun get(): HttpClient {
        return client
    }
}