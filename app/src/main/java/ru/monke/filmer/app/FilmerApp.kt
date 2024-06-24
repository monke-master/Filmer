package ru.monke.filmer.app

import android.app.Application
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent

class FilmerApp: Application() {

    val appComponent = DaggerApplicationComponent.builder().build()

    override fun onCreate() {
        super.onCreate()
    }

}

fun Application.appComponent(): ApplicationComponent {
    return (this as FilmerApp).appComponent
}