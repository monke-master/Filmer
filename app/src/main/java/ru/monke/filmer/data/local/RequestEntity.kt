package ru.monke.filmer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "requests")
data class RequestEntity(
    @PrimaryKey val genreId: String,
    val showId: String,
    val lastDate: Long
)