package com.smog.courseproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviecasts")
data class MovieCastEntity(
    @PrimaryKey
    var id: Int? = null,

    var castIds: List<Int>? = null,
)