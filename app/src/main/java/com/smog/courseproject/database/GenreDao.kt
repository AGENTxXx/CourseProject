package com.smog.courseproject.database

import androidx.room.*
import com.smog.courseproject.data.GenreEntity

@Dao
interface GenreDao {
    @get:Query("SELECT * FROM genres ORDER BY id ASC")
    val all: List<GenreEntity?>?

    @Query("SELECT * FROM genres WHERE id = :id")
    fun getById(id: Long): GenreEntity?

    @Query("SELECT COUNT(*) FROM genres")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: GenreEntity?)

    @Update
    fun update(message: GenreEntity?)

    @Delete
    fun delete(message: GenreEntity?)
}