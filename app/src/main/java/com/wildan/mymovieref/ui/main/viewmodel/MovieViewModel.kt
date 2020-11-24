package com.wildan.mymovieref.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.data.local.FavoriteMovies
import com.wildan.mymovieref.data.local.FavoriteTVSeries
import com.wildan.mymovieref.data.repository.LocalRepository
import com.wildan.mymovieref.data.repository.RemoteRepository
import com.wildan.mymovieref.utils.Constants
import com.wildan.mymovieref.utils.Resource
import com.wildan.mymovieref.utils.errorLog
import kotlinx.coroutines.Dispatchers

class MovieViewModel @ViewModelInject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) :
    ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<FavoriteMovies>> =
        LivePagedListBuilder(localRepository.getFavoriteMovies(), 10).build()

    fun getFavoriteTVSeries(): LiveData<PagedList<FavoriteTVSeries>> =
        LivePagedListBuilder(localRepository.getFavoriteTVSeries(), 10).build()

    fun getMoviePopularList(page: Int) = liveData(Dispatchers.IO) {
        //for testing
        emit(Resource.loading(data = null))
        when (val data =
            remoteRepository.getPopularMovies(
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
            remoteRepository.getPopularTVSeries(
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