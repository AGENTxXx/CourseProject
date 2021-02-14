package com.smog.courseproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smog.courseproject.data.MovieCastEntity

@Dao
interface MovieCastDao {

    @Query("SELECT * FROM moviecasts WHERE id = :id")
    suspend fun getByMovieId(id: Long): MovieCastEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MovieCastEntity)

}