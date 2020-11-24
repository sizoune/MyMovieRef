package com.wildan.mymovieref.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favMovie: FavoriteMovies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteSeries(favTV: FavoriteTVSeries)

    @Delete
    suspend fun deleteMovie(favMovie: FavoriteMovies)

    @Delete
    suspend fun deleteTV(favTV: FavoriteTVSeries)

    @Query("SELECT * from FavoriteMovies")
    fun getFavMovies(): DataSource.Factory<Int, FavoriteMovies>

    @Query("SELECT * from FavoriteTVSeries")
    fun getFavSeries(): DataSource.Factory<Int, FavoriteTVSeries>

    @Query("SELECT * from FavoriteMovies WHERE id=:movieID")
    fun isInFavMovie(movieID: Int): LiveData<FavoriteMovies>

    @Query("SELECT * from FavoriteTVSeries WHERE id=:tvID")
    fun isInFavSeries(tvID: Int): LiveData<FavoriteTVSeries>
}