package com.wildan.mymovieref.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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
    fun getFavMovies(): Flow<List<FavoriteMoviesEntity>>

    @Query("SELECT * from FavoriteTVSeriesEntity")
    fun getFavSeries(): Flow<List<FavoriteTVSeriesEntity>>

    @Query("SELECT * from FavoriteMoviesEntity WHERE id=:movieID")
    fun isInFavMovie(movieID: Int): Flow<FavoriteMoviesEntity>

    @Query("SELECT * from FavoriteTVSeriesEntity WHERE id=:tvID")
    fun isInFavSeries(tvID: Int): Flow<FavoriteTVSeriesEntity>
}