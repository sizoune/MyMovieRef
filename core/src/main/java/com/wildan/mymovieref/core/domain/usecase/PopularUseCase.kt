package com.wildan.mymovieref.core.domain.usecase

import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.data.model.ErrorResponse
import com.wildan.mymovieref.core.data.model.ResponseListObject
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.model.PopularMovie
import com.wildan.mymovieref.core.domain.model.PopularTVSeries
import kotlinx.coroutines.flow.Flow

interface PopularUseCase {
    suspend fun addFavMovie(favoriteMovies: DetailPopularMovie)
    suspend fun addFavSeries(favoriteTVSeries: DetailPopularTVSeries)
    suspend fun deleteFavMovie(favoriteMovies: DetailPopularMovie)
    suspend fun deleteFavSeries(favoriteTVSeries: DetailPopularTVSeries)
    fun getFavoriteMovies(): Flow<List<DetailPopularMovie?>>
    fun getFavoriteTVSeries(): Flow<List<DetailPopularTVSeries?>>
    fun isInFavMovie(movieID: Int): Flow<DetailPopularMovie?>
    fun isInFavSeries(tvID: Int): Flow<DetailPopularTVSeries?>
    suspend fun getPopularMovies(page: Int): Flow<NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse>>
    suspend fun getPopularTVSeries(page: Int): Flow<NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse>>
    suspend fun getDetailMovie(movieID: Int): Flow<NetworkResponse<DetailPopularMovie, ErrorResponse>>
    suspend fun getDetailTVSeries(tvID: Int): Flow<NetworkResponse<DetailPopularTVSeries, ErrorResponse>>
}