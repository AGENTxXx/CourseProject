package com.smog.courseproject.data.network.api

import com.smog.courseproject.BuildConfig
import com.smog.courseproject.data.Credits
import com.smog.courseproject.data.Genres
import com.smog.courseproject.data.Popular
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<Genres>

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") pageNum: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<Popular>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<Credits>
}
