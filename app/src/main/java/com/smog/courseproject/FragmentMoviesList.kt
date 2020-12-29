package com.smog.courseproject

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smog.courseproject.data.Movie
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {
    private var listener: CardFragmentClickListener? = null
    private lateinit var adapter:MovieListAdapter
    private lateinit var viewModel: MoviesListViewModel

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

        viewModel = MoviesListViewModel()
        adapter = MovieListAdapter {
            listener?.cardClick(it)
        }
        val rv: RecyclerView = view.findViewById(R.id.fragment_movies_list_rv)
        val count = when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }

        rv.layoutManager = GridLayoutManager(context, count)
        rv.adapter = adapter
        fetchMovies()
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            viewModel.fetchMovies(requireContext()).distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface CardFragmentClickListener {
        fun cardClick(movie: Movie)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentMoviesList()
    }
}