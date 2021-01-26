package com.wildan.mymovieref.core.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favMovie: FavoriteMoviesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteSeries(favTV: FavoriteTVSeriesEntity)

    @Delete
    suspend fun deleteMovie(favMovie: FavoriteMoviesEntity)

    @Delete
    suspend fun deleteTV(favTV: FavoriteTVSeriesEntity)

    @Query("SELECT * from FavoriteMoviesEntity")
    fun getFavMovies(): DataSource.Factory<Int, FavoriteMoviesEntity>

    @Query("SELECT * from FavoriteTVSeriesEntity")
    fun getFavSeries(): DataSource.Factory<Int, FavoriteTVSeriesEntity>

    @Query("SELECT * from FavoriteMoviesEntity WHERE id=:movieID")
    fun isInFavMovie(movieID: Int): LiveData<FavoriteMoviesEntity>

    @Query("SELECT * from FavoriteTVSeriesEntity WHERE id=:tvID")
    fun isInFavSeries(tvID: Int): LiveData<FavoriteTVSeriesEntity>
}