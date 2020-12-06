package com.smog.courseproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.card.MaterialCardView

class FragmentMoviesList : Fragment() {
    private var listener: CardFragmentClickListener? = null

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

        val cv = view.findViewById<MaterialCardView>(R.id.fragment_movie_list_cv_movie)

        cv.setOnClickListener {
            listener?.cardClick()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface CardFragmentClickListener {
        fun cardClick()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentMoviesList()
    }
}