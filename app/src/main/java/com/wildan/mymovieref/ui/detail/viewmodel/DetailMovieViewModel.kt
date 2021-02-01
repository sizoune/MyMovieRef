package com.wildan.mymovieref.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase
import com.wildan.mymovieref.core.utils.Constants
import com.wildan.mymovieref.core.utils.Resource
import com.wildan.mymovieref.core.utils.errorLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class DetailMovieViewModel constructor(
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

    fun checkFavMovie(movieId: Int) = repository.isInFavMovie(movieId).asLiveData()

    fun checkFavTV(tvID: Int) = repository.isInFavSeries(tvID).asLiveData()

    fun getDetailMovie(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        repository.getDetailMovie(
            movieId
        ).collect { data ->
            when (data) {
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

    fun getDetailTV(tvID: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        repository.getDetailTVSeries(
            tvID
        ).collect { data ->
            when (data) {
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


}