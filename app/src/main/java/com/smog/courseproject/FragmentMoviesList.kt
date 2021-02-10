package com.smog.courseproject

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
import com.smog.courseproject.MainActivity.Companion.dbApp
import com.smog.courseproject.data.Credits
import com.smog.courseproject.data.Genres
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.Popular
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class FragmentMoviesList : Fragment() {
    private var listener: CardFragmentClickListener? = null
    private var adapter: MovieListAdapter = MovieListAdapter {
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
        val count = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }

        rv.layoutManager = GridLayoutManager(context, count)
        rv.adapter = adapter

        val genreDao = dbApp.genreDao()

        lifecycleScope.launch {
            try {
                val result = RetrofitModule.moviesApi.getGenres()

                result.body()?.genres?.forEach {
                    genreDao?.insert(it)
                    genres[it.id] = it.name
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message!!)
                genreDao?.all?.forEach {
                    genres[it!!.id] = it.name
                }
            }

            fetchMovies()
        }
    }

    private suspend fun fetchMovies() {
        viewModel.fetchMovies(requireActivity().applicationContext).distinctUntilChanged()
            .collectLatest {
                adapter.submitData(it)
            }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface CardFragmentClickListener {
        fun cardClick(movie: MovieEntity)
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
    suspend fun getGenres(): Response<Genres>

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovies(@Query("page") pageNum: Int): Response<Popular>

    @GET("movie/{movieId}/credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): Response<Credits>
}

internal object RetrofitModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesApi: MoviesApi = retrofit.create()
}