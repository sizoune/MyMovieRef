package com.wildan.mymovieref.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase
import com.wildan.mymovieref.core.utils.Constants
import com.wildan.mymovieref.core.utils.Resource
import com.wildan.mymovieref.core.utils.errorLog
import kotlinx.coroutines.Dispatchers

class DetailMovieViewModel @ViewModelInject constructor(
    private val repository: PopularUseCase
) :
    ViewModel() {

    suspend fun addFavMovie(favoriteMovies: DetailPopularMovie) =
        repository.addFavMovie(favoriteMovies)

    suspend fun addFavTV(favoriteTVSeries: DetailPopularTVSeries) =
        repository.addFavSeries(favoriteTVSeries)

    suspend fun deleteFavMovie(favoriteMovies: DetailPopularMovie) =
        repository.deleteFavMovie(favoriteMovies)

    suspend fun deleteFavTV(favoriteTVSeries: DetailPopularTVSeries) =
        repository.deleteFavSeries(favoriteTVSeries)

    fun checkFavMovie(movieId: Int) = repository.isInFavMovie(movieId)

    fun checkFavTV(tvID: Int) = repository.isInFavSeries(tvID)

    fun getDetailMovie(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        when (val data =
            repository.getDetailMovie(
                movieId
            )) {
            is NetworkResponse.Success -> {
                emit(Resource.success(data = data.body))
            }
            is NetworkResponse.ServerError -> {
                data.body?.statusMessage?.let { "errorServer".errorLog(it) }
                emit(
                    Resource.errorServer(
                        data = null,
                        message = data.body?.statusMessage
                            ?: Constants.DEFAULT_SERVER_ERROR_MESSAGE
                    )
                )
            }
            is NetworkResponse.NetworkError -> {
                data.error.localizedMessage?.let { "NetworkError".errorLog(it) }
                emit(
                    Resource.errorNetwork(
                        data = null,
                        message = Constants.DEFAULT_NETWORK_ERROR_MESSAGE
                    )
                )
            }
            is NetworkResponse.UnknownError -> {
                data.error.localizedMessage?.let { "UnknownError".errorLog(it) }
                emit(
                    Resource.errorUnknown(
                        data = null,
                        message = Constants.DEFAULT_UNKNOWN_ERROR_MESSAGE
                    )
                )
            }
        }
    }

    fun getDetailTV(tvID: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        when (val data =
            repository.getDetailTVSeries(
                tvID
            )) {
            is NetworkResponse.Success -> {
                emit(Resource.success(data = data.body))
            }
            is NetworkResponse.ServerError -> {
                data.body?.statusMessage?.let { "errorServer".errorLog(it) }
                emit(
                    Resource.errorServer(
                        data = null,
                        message = data.body?.statusMessage
                            ?: Constants.DEFAULT_SERVER_ERROR_MESSAGE
                    )
                )
            }
            is NetworkResponse.NetworkError -> {
                data.error.localizedMessage?.let { "NetworkError".errorLog(it) }
                emit(
                    Resource.errorNetwork(
                        data = null,
                        message = Constants.DEFAULT_NETWORK_ERROR_MESSAGE
                    )
                )
            }
            is NetworkResponse.UnknownError -> {
                data.error.localizedMessage?.let { "UnknownError".errorLog(it) }
                emit(
                    Resource.errorUnknown(
                        data = null,
                        message = Constants.DEFAULT_UNKNOWN_ERROR_MESSAGE
                    )
                )
            }
        }
    }


}