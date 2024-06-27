package ru.monke.filmer.app

import android.app.Application
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent


class FilmerApp: Application() {

    val appComponent = DaggerApplicationComponent
                                            .builder()
                                            .context(this)
                                            .build()
}

fun Application.appComponent(): ApplicationComponent {
    return (this as FilmerApp).appComponent
}