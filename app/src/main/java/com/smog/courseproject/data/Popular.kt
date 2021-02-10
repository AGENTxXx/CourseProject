package com.smog.courseproject.data

import com.google.gson.annotations.SerializedName

data class Popular(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<MovieEntity>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)