package com.smog.courseproject.database

import androidx.room.*
import com.smog.courseproject.data.MovieEntity

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM movies ORDER BY id ASC")
    val all: List<MovieEntity>?

    @Query("SELECT * FROM movies LIMIT :limit OFFSET :offset")
    fun getLimitItems(limit:Int, offset:Int): List<MovieEntity>?

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Long): MovieEntity?

    @Query("SELECT COUNT(*) FROM movies")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MovieEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<MovieEntity>?)

    @Update
    fun update(message: MovieEntity?)

    @Delete
    fun delete(message: MovieEntity?)
}