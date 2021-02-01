package com.wildan.mymovieref.core.data.repository

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PopularRepository constructor(
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

    override fun getFavoriteMovies(): Flow<List<DetailPopularMovie?>> = flow {
        favDao.getFavMovies().collect {
            emit(DataMapper.mapDetailMovieFavoriteToDomain(it))
        }
    }.flowOn(Dispatchers.IO)

    override fun getFavoriteTVSeries(): Flow<List<DetailPopularTVSeries?>> = flow {
        favDao.getFavSeries().collect {
            emit(DataMapper.mapDetailTVFavoriteToDomain(it))
        }
    }.flowOn(Dispatchers.IO)


    override fun isInFavMovie(movieID: Int): Flow<DetailPopularMovie?> = flow {
        favDao.isInFavMovie(movieID).collect {
            emit(DataMapper.mapDetailMoviesFavoriteToDomain(it))
        }
    }.flowOn(Dispatchers.IO)

    override fun isInFavSeries(tvID: Int): Flow<DetailPopularTVSeries?> = flow {
        favDao.isInFavSeries(tvID).collect {
            emit(DataMapper.mapDetailSeriesFavoriteToDomain(it))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPopularMovies(page: Int): Flow<NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse>> {
        return flow {
            when (val data =
                apiService.getPopularMovies(
                    Constants.API_KEY, page
                )) {
                is NetworkResponse.Success -> {
                    val mappedData =
                        ResponseListObject(DataMapper.mapPopularMovieResponseToDomain(data.body.results))
                    emit(
                        NetworkResponse.Success(
                            mappedData,
                            null,
                            200
                        )
                    )
                }
                is NetworkResponse.ServerError -> {
                    emit(NetworkResponse.ServerError(data.body, data.code))
                }
                is NetworkResponse.NetworkError -> {
                    emit(NetworkResponse.NetworkError(data.error))
                }
                is NetworkResponse.UnknownError -> {
                    emit(NetworkResponse.UnknownError(data.error))
                }
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun getPopularTVSeries(page: Int): Flow<NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse>> {
        return flow {
            when (val data =
                apiService.getPopularTVSeries(
                    Constants.API_KEY, page
                )) {
                is NetworkResponse.Success -> {
                    val mappedData =
                        ResponseListObject(DataMapper.mapPopularSeriesResponseToDomain(data.body.results))
                    emit(
                        NetworkResponse.Success(
                            mappedData,
                            null,
                            200
                        )
                    )
                }
                is NetworkResponse.ServerError -> {
                    emit(NetworkResponse.ServerError(data.body, data.code))
                }
                is NetworkResponse.NetworkError -> {
                    emit(NetworkResponse.NetworkError(data.error))
                }
                is NetworkResponse.UnknownError -> {
                    emit(NetworkResponse.UnknownError(data.error))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailMovie(movieID: Int): Flow<NetworkResponse<DetailPopularMovie, ErrorResponse>> {
        return flow {
            when (val data =
                apiService.getDetailMovie(
                    movieID, Constants.API_KEY
                )) {
                is NetworkResponse.Success -> {
                    val mappedData =
                        DataMapper.mapDetailMovieResponseToDomain(data.body)
                    emit(NetworkResponse.Success(mappedData, null, 200))
                }
                is NetworkResponse.ServerError -> {
                    emit(NetworkResponse.ServerError(data.body, data.code))
                }
                is NetworkResponse.NetworkError -> {
                    emit(NetworkResponse.NetworkError(data.error))
                }
                is NetworkResponse.UnknownError -> {
                    emit(NetworkResponse.UnknownError(data.error))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailTVSeries(tvID: Int): Flow<NetworkResponse<DetailPopularTVSeries, ErrorResponse>> {
        return flow {
            when (val data =
                apiService.getDetailTV(
                    tvID, Constants.API_KEY
                )) {
                is NetworkResponse.Success -> {
                    val mappedData =
                        DataMapper.mapDetailSeriesResponseToDomain(data.body)
                    emit(NetworkResponse.Success(mappedData, data.headers, 200))
                }
                is NetworkResponse.ServerError -> {
                    emit(NetworkResponse.ServerError(data.body, data.code))
                }
                is NetworkResponse.NetworkError -> {
                    emit(NetworkResponse.NetworkError(data.error))
                }
                is NetworkResponse.UnknownError -> {
                    emit(NetworkResponse.UnknownError(data.error))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}