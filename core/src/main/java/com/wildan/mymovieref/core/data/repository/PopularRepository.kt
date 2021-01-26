package com.wildan.mymovieref.core.data.repository

import androidx.lifecycle.map
import androidx.paging.LivePagedListBuilder
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.data.local.FavoriteDao
import com.wildan.mymovieref.core.data.model.ErrorResponse
import com.wildan.mymovieref.core.data.model.ResponseListObject
import com.wildan.mymovieref.core.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.model.PopularMovie
import com.wildan.mymovieref.core.domain.model.PopularTVSeries
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import com.wildan.mymovieref.core.utils.Constants
import com.wildan.mymovieref.core.utils.DataMapper
import javax.inject.Inject

class PopularRepository @Inject constructor(
    private val apiService: TheMovieDBAPI,
    private val favDao: FavoriteDao
) : IPopularRepository {

    override suspend fun addFavMovie(favoriteMovies: DetailPopularMovie) =
        favDao.addFavoriteMovie(DataMapper.mapDetailMovieToFavorite(favoriteMovies))

    override suspend fun addFavSeries(favoriteTVSeries: DetailPopularTVSeries) =
        favDao.addFavoriteSeries(DataMapper.mapDetailSeriesToFavorite(favoriteTVSeries))

    override suspend fun deleteFavMovie(favoriteMovies: DetailPopularMovie) =
        favDao.deleteMovie(DataMapper.mapDetailMovieToFavorite(favoriteMovies))

    override suspend fun deleteFavSeries(favoriteTVSeries: DetailPopularTVSeries) =
        favDao.deleteTV(DataMapper.mapDetailSeriesToFavorite(favoriteTVSeries))

    override fun getFavoriteMovies() =
        LivePagedListBuilder(
            DataMapper.mapDetailMovieFavoriteToDomain(favDao.getFavMovies()),
            10
        ).build()

    override fun getFavoriteTVSeries() = LivePagedListBuilder(
        DataMapper.mapDetailTVFavoriteToDomain(favDao.getFavSeries()),
        10
    ).build()


    override fun isInFavMovie(movieID: Int) = favDao.isInFavMovie(movieID).map {
        DataMapper.mapDetailMoviesFavoriteToDomain(it)
    }

    override fun isInFavSeries(tvID: Int) = favDao.isInFavSeries(tvID).map {
        DataMapper.mapDetailSeriesFavoriteToDomain(it)
    }

    override suspend fun getPopularMovies(page: Int): NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse> {
        when (val data =
            apiService.getPopularMovies(
                Constants.API_KEY, page
            )) {
            is NetworkResponse.Success -> {
                val mappedData =
                    ResponseListObject(DataMapper.mapPopularMovieResponseToDomain(data.body.results))
                return NetworkResponse.Success(
                    mappedData,
                    null,
                    200
                )
            }
            is NetworkResponse.ServerError -> {
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

    override suspend fun getPopularTVSeries(page: Int): NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse> {
        when (val data =
            apiService.getPopularTVSeries(
                Constants.API_KEY, page
            )) {
            is NetworkResponse.Success -> {
                val mappedData =
                    ResponseListObject(DataMapper.mapPopularSeriesResponseToDomain(data.body.results))
                return NetworkResponse.Success(
                    mappedData,
                    null,
                    200
                )
            }
            is NetworkResponse.ServerError -> {
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

    override suspend fun getDetailMovie(movieID: Int): NetworkResponse<DetailPopularMovie, ErrorResponse> {
        when (val data =
            apiService.getDetailMovie(
                movieID, Constants.API_KEY
            )) {
            is NetworkResponse.Success -> {
                val mappedData =
                    DataMapper.mapDetailMovieResponseToDomain(data.body)
                return NetworkResponse.Success(mappedData, null, 200)
            }
            is NetworkResponse.ServerError -> {
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

    override suspend fun getDetailTVSeries(tvID: Int): NetworkResponse<DetailPopularTVSeries, ErrorResponse> {
        when (val data =
            apiService.getDetailTV(
                tvID, Constants.API_KEY
            )) {
            is NetworkResponse.Success -> {
                val mappedData =
                    DataMapper.mapDetailSeriesResponseToDomain(data.body)
                return NetworkResponse.Success(mappedData, data.headers, 200)
            }
            is NetworkResponse.ServerError -> {
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

}