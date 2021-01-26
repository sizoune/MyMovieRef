package com.wildan.mymovieref.core.domain.model

data class DetailPopularTVSeries(
    val id: Int,
    val backdropPath: String,
    val posterPath: String,
    val originalName: String,
    val firstAirDate: String,
    val overview: String,
    val genres: List<Genre>
)
