package com.wildan.mymovieref.data.repository

import com.wildan.mymovieref.data.local.FavoriteDao
import com.wildan.mymovieref.data.local.FavoriteMovies
import com.wildan.mymovieref.data.local.FavoriteTVSeries
import javax.inject.Inject

class LocalRepository @Inject constructor(private val favDao: FavoriteDao) {

    suspend fun addFavMovie(favoriteMovies: FavoriteMovies) =
        favDao.addFavoriteMovie(favoriteMovies)

    suspend fun addFavSeries(favoriteTVSeries: FavoriteTVSeries) =
        favDao.addFavoriteSeries(favoriteTVSeries)

    suspend fun deleteFavMovie(favoriteMovies: FavoriteMovies) = favDao.deleteMovie(favoriteMovies)

    suspend fun deleteFavSeries(favoriteTVSeries: FavoriteTVSeries) =
        favDao.deleteTV(favoriteTVSeries)

    fun getFavoriteMovies() = favDao.getFavMovies()

    fun getFavoriteTVSeries() = favDao.getFavSeries()

    fun isInFavMovie(movieID: Int) = favDao.isInFavMovie(movieID)

    fun isInFavSeries(tvID: Int) = favDao.isInFavSeries(tvID)
}