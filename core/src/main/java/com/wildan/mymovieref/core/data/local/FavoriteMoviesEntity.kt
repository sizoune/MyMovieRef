package com.wildan.mymovieref.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wildan.mymovieref.core.data.model.GenreResponse

@Entity
data class FavoriteMoviesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String,

    @ColumnInfo(name = "poster_path")
    val poster_path: String,

    @ColumnInfo(name = "original_title")
    val original_title: String,

    @ColumnInfo(name = "release_date")
    val release_date: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "genres")
    val genres: List<GenreResponse>
)