package com.wildan.mymovieref.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase
import com.wildan.mymovieref.core.utils.Constants
import com.wildan.mymovieref.core.utils.Resource
import com.wildan.mymovieref.core.utils.errorLog
import kotlinx.coroutines.Dispatchers

class MovieViewModel @ViewModelInject constructor(
    private val popularUseCase: PopularUseCase
) :
    ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<DetailPopularMovie>> =
        popularUseCase.getFavoriteMovies()

    fun getFavoriteTVSeries(): LiveData<PagedList<DetailPopularTVSeries>> =
        popularUseCase.getFavoriteTVSeries()

    fun getMoviePopularList(page: Int) = liveData(Dispatchers.IO) {
        //for testing
        emit(Resource.loading(data = null))
        when (val data =
            popularUseCase.getPopularMovies(
                page
            )) {
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

    fun getTVSeriesPopularList(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        when (val data =
            popularUseCase.getPopularTVSeries(
                page
            )) {
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