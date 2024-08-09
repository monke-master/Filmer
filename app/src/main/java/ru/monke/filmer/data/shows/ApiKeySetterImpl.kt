package ru.monke.filmer.data.shows

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.monke.filmer.data.shows.models.ServiceNotAvailableException
import ru.monke.filmer.di.ClassProvider
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ApiKeySetterImpl"

@Singleton
class ApiKeySetterImpl @Inject constructor(
    private val ktorProvider: ClassProvider<HttpClient>
): ApiKeySetter() {

    private val availableKeys = listOf(
        "3ea3eb332amsh135d05e8b4a6a84p18e2b1jsn0660252f199f", // Svinkter
        "4ccc906dbbmshc6cb6a39df81420p1d2e4bjsn7a5c6777899c",
        "49720df865mshcc4905744faf50bp1d3f1ejsn5d00fc98f84d",
        "a22d4e01dcmsh4fc3c75138b4fc9p1dbf8fjsn1d91c71bad90",
        "239825a405msh0998c1425df4b31p1483b7jsn7a0e17c3c493"
    )

    private val keyQueue: Queue<String> = LinkedList(availableKeys)

    init {
        Log.d(TAG, "init")
        key = keyQueue.poll() ?: "unknown key"
    }

    override suspend fun nextKey(): Result<Unit> {
        return checkKey(key).fold(
            onSuccess = { successAuth ->
                if (successAuth) return Result.success(Unit)
                key = keyQueue.poll() ?: return Result.failure(ServiceNotAvailableException())
                Log.d(TAG, "set: $key")
                nextKey()
            },
            onFailure = { error ->
                return Result.failure(error)
            }
        )
    }

    private suspend fun checkKey(key: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val result = ktorProvider.get().request {
                    url.takeFrom(GENRES_ENDPOINT)
                    header(RAPID_API_KEY, key)
                }

                Log.d(TAG, "$key: $result")
                if (result.status.isSuccess()) {
                    return@withContext Result.success(true)
                }

                return@withContext Result.success(false)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

}