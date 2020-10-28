package com.wildan.mymovieref.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val backdropPath: String,
    val id: Int,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val vote: Double,
    val urlDetail:String
) : Parcelable