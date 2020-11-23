package com.wildan.mymovieref.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.data.local.FavoriteMovies
import com.wildan.mymovieref.data.local.FavoriteTVSeries
import com.wildan.mymovieref.data.repository.LocalRepository
import com.wildan.mymovieref.data.repository.RemoteRepository
import com.wildan.mymovieref.utils.Constants
import com.wildan.mymovieref.utils.Resource
import com.wildan.mymovieref.utils.errorLog
import kotlinx.coroutines.Dispatchers

class DetailMovieViewModel @ViewModelInject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) :
    ViewModel() {

    suspend fun addFavMovie(favoriteMovies: FavoriteMovies) =
        localRepository.addFavMovie(favoriteMovies)

    suspend fun addFavTV(favoriteTVSeries: FavoriteTVSeries) =
        localRepository.addFavSeries(favoriteTVSeries)

    suspend fun deleteFavMovie(favoriteMovies: FavoriteMovies) =
        localRepository.deleteFavMovie(favoriteMovies)

    suspend fun deleteFavTV(favoriteTVSeries: FavoriteTVSeries) =
        localRepository.deleteFavSeries(favoriteTVSeries)

    fun checkFavMovie(movieId: Int) = localRepository.isInFavMovie(movieId)

    fun checkFavTV(tvID: Int) = localRepository.isInFavSeries(tvID)

    fun getDetailMovie(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        when (val data =
            remoteRepository.getDetailMovie(
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
            remoteRepository.getDetailTVSeries(
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