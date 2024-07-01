package ru.monke.filmer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RequestEntity::class],
    version = 1
)
abstract class FilmerDatabase: RoomDatabase() {
    abstract fun requestDAO(): RequestDAO
}