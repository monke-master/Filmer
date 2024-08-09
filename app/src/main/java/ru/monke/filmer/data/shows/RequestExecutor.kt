package ru.monke.filmer.data.shows

import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class RequestExecutor @Inject constructor(
    private val apiKeySetter: ApiKeySetter
) {

    suspend fun executeRequest(
        request: suspend (String) -> HttpResponse
    ): HttpResponse {
        val response = request.invoke(apiKeySetter.key)
        if (response.status == HttpStatusCode.TooManyRequests) {

            val result = apiKeySetter.nextKey().getOrElse {
                return response
            }
            return executeRequest(request)
        }
        return response
    }

}