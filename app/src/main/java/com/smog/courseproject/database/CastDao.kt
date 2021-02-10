package com.smog.courseproject.database

import androidx.room.*
import com.smog.courseproject.data.CastEntity

@Dao
interface CastDao {
    @get:Query("SELECT * FROM casts ORDER BY id ASC")
    val all: List<CastEntity>?

    @Query("SELECT * FROM casts WHERE id = :id")
    fun getById(id: Long): CastEntity?

    @Query("SELECT * FROM casts WHERE castId IN (:ids)")
    fun getByIds(ids:List<Int>?):List<CastEntity>?

    @Query("SELECT COUNT(*) FROM casts")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: CastEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<CastEntity>?)

    @Update
    fun update(message: CastEntity?)

    @Delete
    fun delete(message: CastEntity?)
}