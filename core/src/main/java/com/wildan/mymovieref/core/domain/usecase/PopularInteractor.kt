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
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import javax.inject.Inject

class PopularInteractor @Inject constructor(private val popularRepository: IPopularRepository) :
    PopularUseCase {

    override suspend fun addFavMovie(favoriteMovies: DetailPopularMovie) =
        popularRepository.addFavMovie(favoriteMovies)

    override suspend fun addFavSeries(favoriteTVSeries: DetailPopularTVSeries) =
        popularRepository.addFavSeries(favoriteTVSeries)

    override suspend fun deleteFavMovie(favoriteMovies: DetailPopularMovie) =
        popularRepository.deleteFavMovie(favoriteMovies)

    override suspend fun deleteFavSeries(favoriteTVSeries: DetailPopularTVSeries) =
        popularRepository.deleteFavSeries(favoriteTVSeries)

    override fun getFavoriteMovies(): LiveData<PagedList<DetailPopularMovie>> =
        popularRepository.getFavoriteMovies()

    override fun getFavoriteTVSeries(): LiveData<PagedList<DetailPopularTVSeries>> =
        popularRepository.getFavoriteTVSeries()

    override fun isInFavMovie(movieID: Int): LiveData<DetailPopularMovie?> =
        popularRepository.isInFavMovie(movieID)

    override fun isInFavSeries(tvID: Int): LiveData<DetailPopularTVSeries?> =
        popularRepository.isInFavSeries(tvID)

    override suspend fun getPopularMovies(page: Int): NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse> =
        popularRepository.getPopularMovies(page)

    override suspend fun getPopularTVSeries(page: Int): NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse> =
        popularRepository.getPopularTVSeries(page)

    override suspend fun getDetailMovie(movieID: Int): NetworkResponse<DetailPopularMovie, ErrorResponse> =
        popularRepository.getDetailMovie(movieID)

    override suspend fun getDetailTVSeries(tvID: Int): NetworkResponse<DetailPopularTVSeries, ErrorResponse> =
        popularRepository.getDetailTVSeries(tvID)
}