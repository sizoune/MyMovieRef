package com.wildan.mymovieref.core.domain.usecase

import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.data.model.ErrorResponse
import com.wildan.mymovieref.core.data.model.ResponseListObject
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.model.PopularMovie
import com.wildan.mymovieref.core.domain.model.PopularTVSeries
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import kotlinx.coroutines.flow.Flow

class PopularInteractor constructor(private val popularRepository: IPopularRepository) :
    PopularUseCase {

    override suspend fun addFavMovie(favoriteMovies: DetailPopularMovie) =
        popularRepository.addFavMovie(favoriteMovies)

    override suspend fun addFavSeries(favoriteTVSeries: DetailPopularTVSeries) =
        popularRepository.addFavSeries(favoriteTVSeries)

    override suspend fun deleteFavMovie(favoriteMovies: DetailPopularMovie) =
        popularRepository.deleteFavMovie(favoriteMovies)

    override suspend fun deleteFavSeries(favoriteTVSeries: DetailPopularTVSeries) =
        popularRepository.deleteFavSeries(favoriteTVSeries)

    override fun getFavoriteMovies(): Flow<List<DetailPopularMovie?>> =
        popularRepository.getFavoriteMovies()

    override fun getFavoriteTVSeries(): Flow<List<DetailPopularTVSeries?>> =
        popularRepository.getFavoriteTVSeries()

    override fun isInFavMovie(movieID: Int): Flow<DetailPopularMovie?> =
        popularRepository.isInFavMovie(movieID)

    override fun isInFavSeries(tvID: Int): Flow<DetailPopularTVSeries?> =
        popularRepository.isInFavSeries(tvID)

    override suspend fun getPopularMovies(page: Int): Flow<NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse>> =
        popularRepository.getPopularMovies(page)

    override suspend fun getPopularTVSeries(page: Int): Flow<NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse>> =
        popularRepository.getPopularTVSeries(page)

    override suspend fun getDetailMovie(movieID: Int): Flow<NetworkResponse<DetailPopularMovie, ErrorResponse>> =
        popularRepository.getDetailMovie(movieID)

    override suspend fun getDetailTVSeries(tvID: Int): Flow<NetworkResponse<DetailPopularTVSeries, ErrorResponse>> =
        popularRepository.getDetailTVSeries(tvID)
}