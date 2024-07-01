package ru.monke.filmer.data.local

import android.content.Context
import androidx.room.Room
import ru.monke.filmer.di.ClassProvider
import javax.inject.Inject

class FilmerDatabaseProvider @Inject constructor(
    context: Context
): ClassProvider<FilmerDatabase> {

    private val database by lazy {
        Room
            .databaseBuilder(
                context = context,
                klass = FilmerDatabase::class.java,
                name = "FilmerDatabase")
            .build()
    }

    override fun get(): FilmerDatabase = database
}