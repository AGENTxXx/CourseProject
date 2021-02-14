package com.smog.courseproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smog.courseproject.data.CastEntity

@Dao
interface CastDao {

    @Query("SELECT * FROM casts WHERE castId IN (:ids)")
    suspend fun getByIds(ids: List<Int>): List<CastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CastEntity>)

}