package com.smog.courseproject.database

import androidx.room.*
import com.smog.courseproject.data.MovieCastEntity

@Dao
interface MovieCastDao {
    @get:Query("SELECT * FROM moviecasts ORDER BY id ASC")
    val all: List<MovieCastEntity>?

    @Query("SELECT * FROM moviecasts WHERE id = :id")
    fun getByMovieId(id: Long): MovieCastEntity?

    @Query("SELECT COUNT(*) FROM moviecasts")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MovieCastEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<MovieCastEntity>?)

    @Update
    fun update(message: MovieCastEntity?)

    @Delete
    fun delete(message: MovieCastEntity?)
}