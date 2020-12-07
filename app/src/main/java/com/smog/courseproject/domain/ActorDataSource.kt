package com.smog.courseproject.domain

import com.smog.courseproject.data.Actor

class ActorDataSource {
    fun getActors(): List<Actor> {
        return listOf(
            Actor("moviep1", "Robert Downey Jr."),
            Actor("moviep2", "Chris Evans"),
            Actor("moviep3", "Mark Ruffalo"),
            Actor("moviep4", "Chris Hemsworth"),
        )
    }
}