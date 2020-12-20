package com.smog.courseproject

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {
    private var listener: CardFragmentClickListener? = null
    private var adapter = MovieListAdapter {
        listener?.cardClick(it)
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

        val rv:RecyclerView = view.findViewById(R.id.fragment_movies_list_rv)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (rv.layoutManager as GridLayoutManager).spanCount = 4
        }
        rv.adapter = adapter
        updateData()
    }


    private fun updateData() {
        CoroutineScope(Dispatchers.Main).launch {
            val movieList = loadMovies(requireContext())
            adapter.bindMovies(movieList)
        }
        //adapter.bindMovies(MovieDataSource().getMovies())
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