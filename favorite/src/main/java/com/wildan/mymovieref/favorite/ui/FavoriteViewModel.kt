package com.wildan.mymovieref.favorite.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase

class FavoriteViewModel @ViewModelInject constructor(
    private val popularUseCase: PopularUseCase
) : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<DetailPopularMovie>> =
        popularUseCase.getFavoriteMovies()

    fun getFavoriteTVSeries(): LiveData<PagedList<DetailPopularTVSeries>> =
        popularUseCase.getFavoriteTVSeries()
}