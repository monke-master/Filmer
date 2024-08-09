package ru.monke.filmer.app

import android.app.Application
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.monke.filmer.data.shows.ApiKeySetter
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent
import javax.inject.Inject


class FilmerApp: Application() {

    val appComponent = DaggerApplicationComponent
                                            .builder()
                                            .context(this)
                                            .build()

    private val apiKeySetter = appComponent.apiKeySetter()

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            apiKeySetter.nextKey()
        }
    }
}

fun Application.appComponent(): ApplicationComponent {
    return (this as FilmerApp).appComponent
}