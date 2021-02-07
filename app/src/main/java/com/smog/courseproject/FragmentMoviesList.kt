package com.smog.courseproject

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.smog.courseproject.data.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class FragmentMoviesList : Fragment() {
    private var listener: CardFragmentClickListener? = null
    private var adapter:MovieListAdapter = MovieListAdapter {
        listener?.cardClick(it)
    }
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CardFragmentClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv: RecyclerView = view.findViewById(R.id.fragment_movies_list_rv)
        val count = when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }

        rv.layoutManager = GridLayoutManager(context, count)
        rv.adapter = adapter

        lifecycleScope.launch {
            val result = RetrofitModule.moviesApi.getGenres()
            result.body()?.genres?.forEach {
                genres[it.id] = it.name
            }

            fetchMovies()
        }
    }

    private suspend fun fetchMovies() {
        viewModel.fetchMovies(requireActivity().applicationContext).distinctUntilChanged().collectLatest {
            adapter.submitData(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface CardFragmentClickListener {
        fun cardClick(movie: MovieDb)
    }

    companion object {
        var genres: ArrayMap<Int, String> = arrayMapOf()
        @JvmStatic
        fun newInstance() =
            FragmentMoviesList()
    }
}

internal interface MoviesApi {
    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovies(@Query("page") pageNum:Int): Response<Popular>

    @GET("movie/{movieId}?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieInfo(@Path("movieId") movieId:Int): Response<MovieExtraDb>

    @GET("movie/{movieId}/credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieCredits(@Path("movieId") movieId:Int): Response<CreditDb>
}

internal object RetrofitModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        //.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesApi: MoviesApi = retrofit.create()
}