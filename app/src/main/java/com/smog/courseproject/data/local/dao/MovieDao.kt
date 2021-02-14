package com.smog.courseproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smog.courseproject.data.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies LIMIT :limit OFFSET :offset")
    suspend fun getLimitItems(limit: Int, offset: Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MovieEntity>)

}