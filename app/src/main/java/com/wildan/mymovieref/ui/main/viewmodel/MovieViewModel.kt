package com.wildan.mymovieref.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase
import com.wildan.mymovieref.core.utils.Constants
import com.wildan.mymovieref.core.utils.Resource
import com.wildan.mymovieref.core.utils.errorLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class MovieViewModel constructor(
    private val popularUseCase: PopularUseCase
) :
    ViewModel() {

    fun getMoviePopularList(page: Int) = liveData(Dispatchers.IO) {
        //for testing
        emit(Resource.loading(data = null))
        popularUseCase.getPopularMovies(
            page
        ).collect { data ->
            when (data) {
                is NetworkResponse.Success -> {
                    emit(Resource.success(data = data.body.results))
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

    fun getTVSeriesPopularList(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        popularUseCase.getPopularTVSeries(
            page
        ).collect { data ->
            when (data) {
                is NetworkResponse.Success -> {
                    emit(Resource.success(data = data.body.results))
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