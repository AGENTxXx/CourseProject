package com.smog.courseproject.domain

import com.smog.courseproject.data.Movie

class MovieDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie("movie_1","13+","Action, Adventure, Drama",4.0f,123,"Strangers",130,false, "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe.","adventures_preview"),
            Movie("movie_2","14+","Action, Adventure, Fantasy",4.0f,223,"Hello moy",131,true, "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."),
            Movie("movie_3","15+","Action, Adventure, Drama",3.5f,132,"Moy boy",130,false, "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."),
            Movie("movie_4","16+","Action, Adventure, Drama",4.0f,423,"Soruce age",150,true, "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."),
            Movie("movie_1","17+","Action, Adventure, Horror",5.0f,523,"Soup Spacy",110,false, "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."),
            Movie("movie_2","18+","Action, Adventure, Drama",4.0f,623,"Best movie",120,false, "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."),
        )
    }
}