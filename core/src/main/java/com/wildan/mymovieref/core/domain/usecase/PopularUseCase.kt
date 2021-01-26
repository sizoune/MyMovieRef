package com.wildan.mymovieref.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.data.model.ErrorResponse
import com.wildan.mymovieref.core.data.model.ResponseListObject
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.model.PopularMovie
import com.wildan.mymovieref.core.domain.model.PopularTVSeries

interface PopularUseCase {
    suspend fun addFavMovie(favoriteMovies: DetailPopularMovie)
    suspend fun addFavSeries(favoriteTVSeries: DetailPopularTVSeries)
    suspend fun deleteFavMovie(favoriteMovies: DetailPopularMovie)
    suspend fun deleteFavSeries(favoriteTVSeries: DetailPopularTVSeries)
    fun getFavoriteMovies(): LiveData<PagedList<DetailPopularMovie>>
    fun getFavoriteTVSeries(): LiveData<PagedList<DetailPopularTVSeries>>
    fun isInFavMovie(movieID: Int): LiveData<DetailPopularMovie?>
    fun isInFavSeries(tvID: Int): LiveData<DetailPopularTVSeries?>
    suspend fun getPopularMovies(page: Int): NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse>
    suspend fun getPopularTVSeries(page: Int): NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse>
    suspend fun getDetailMovie(movieID: Int): NetworkResponse<DetailPopularMovie, ErrorResponse>
    suspend fun getDetailTVSeries(tvID: Int): NetworkResponse<DetailPopularTVSeries, ErrorResponse>
}