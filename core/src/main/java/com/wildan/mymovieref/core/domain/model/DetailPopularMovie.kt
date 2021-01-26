package com.wildan.mymovieref.core.domain.model

data class DetailPopularMovie(
    val id: Int,
    val backdropPath: String,
    val posterPath: String,
    val originalTitle: String,
    val releaseDate: String,
    val overview: String,
    val genres: List<Genre>
)
