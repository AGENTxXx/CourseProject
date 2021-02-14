package com.smog.courseproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smog.courseproject.data.*
import com.smog.courseproject.data.local.converters.GenreConverter
import com.smog.courseproject.data.local.dao.*

@Database(
    entities = [
        CrewEntity::class,
        CastEntity::class,
        MovieEntity::class,
        GenreEntity::class,
        MovieCastEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun castDao(): CastDao
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun movieCastDao(): MovieCastDao
}