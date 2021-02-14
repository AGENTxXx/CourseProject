package com.smog.courseproject.data.local.converters

import androidx.room.TypeConverter

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