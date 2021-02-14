package com.smog.courseproject.presentation.screens.movielist

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
import com.smog.courseproject.R
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.network.RetrofitModule
import com.smog.courseproject.presentation.App
import com.smog.courseproject.presentation.App.Companion.dbApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {

    private var listener: CardFragmentClickListener? = null
    private var adapter: MovieListAdapter = MovieListAdapter { listener?.cardClick(it) }
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            App.dbApp.movieDao(),
            RetrofitModule.moviesApi
        )
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

        //TODO спрячь это во вьюмодель, и избавься от статического genres здесь
        lifecycleScope.launch {
            try {
                val result = RetrofitModule.moviesApi.getGenres()

                result.body()?.genres?.forEach {
                    genreDao.insert(it)
                    genres[it.id] = it.name
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message!!)
                genreDao.getAll().forEach {
                    genres[it.id] = it.name
                }
            }

            fetchMovies()
        }
    }

    private suspend fun fetchMovies() {
        viewModel.fetchMovies().distinctUntilChanged()
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
        fun newInstance() = FragmentMoviesList()
    }
}