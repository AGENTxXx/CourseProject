package com.smog.courseproject.database

import androidx.room.*
import com.smog.courseproject.data.CrewEntity

@Dao
interface CrewDao {
    @get:Query("SELECT * FROM crews ORDER BY id ASC")
    val all: List<CrewEntity?>?

    @Query("SELECT * FROM crews WHERE id = :id")
    fun getById(id: Long): CrewEntity?

    @Query("SELECT COUNT(*) FROM crews")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: CrewEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<CrewEntity>?)

    @Update
    fun update(message: CrewEntity?)

    @Delete
    fun delete(message: CrewEntity?)
}