package ru.monke.filmer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RequestDAO {

    @Query("SELECT * FROM requests WHERE genreId = :genreId")
    suspend fun getRequestByGenre(genreId: String): RequestEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: RequestEntity)
}