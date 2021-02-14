package com.smog.courseproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smog.courseproject.data.GenreEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres ORDER BY id ASC")
    suspend fun getAll(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: GenreEntity)

}