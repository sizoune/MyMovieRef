package com.wildan.mymovieref.core.domain.model

data class PopularTVSeries(
    val id: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val originalName: String?,
    val firstAirDate: String?,
    val overview: String?
)
