package com.smog.courseproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.smog.courseproject.data.*

@Database(
    entities = [CrewEntity::class, CastEntity::class, MovieEntity::class, GenreEntity::class, MovieCastEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun crewDao(): CrewDao?
    abstract fun castDao(): CastDao?
    abstract fun movieDao(): MovieDao?
    abstract fun genreDao(): GenreDao?
    abstract fun movieCastDao(): MovieCastDao?
}

class GenreConverter {
    @TypeConverter
    fun fromGenres(genresId: List<Int>): String {
        return genresId.joinToString()
    }

    @TypeConverter
    fun toGenres(data: String): List<Int> {
        return if (data.isEmpty()) listOf() else data.split(", ").map { it.toInt() }
    }
}