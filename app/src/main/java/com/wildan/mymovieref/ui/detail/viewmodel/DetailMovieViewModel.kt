package com.wildan.mymovieref.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.data.repository.RemoteRepository
import com.wildan.mymovieref.utils.Constants
import com.wildan.mymovieref.utils.EspressoIdlingResource
import com.wildan.mymovieref.utils.Resource
import com.wildan.mymovieref.utils.errorLog
import kotlinx.coroutines.Dispatchers

class DetailMovieViewModel @ViewModelInject constructor(private val remoteRepository: RemoteRepository) :
    ViewModel() {

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