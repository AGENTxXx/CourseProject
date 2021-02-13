package com.smog.courseproject.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Genres(

	@field:SerializedName("genres")
	val genres: List<GenreEntity>? = null
)

@Entity(tableName = "genres")
@Parcelize
data class GenreEntity(

	@field:SerializedName("name")
	val name: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
