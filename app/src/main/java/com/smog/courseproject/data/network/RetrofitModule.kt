package com.smog.courseproject.data.network

import com.smog.courseproject.BuildConfig
import com.smog.courseproject.data.network.api.MoviesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


internal object RetrofitModule {

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesApi: MoviesApi = retrofit.create()
}