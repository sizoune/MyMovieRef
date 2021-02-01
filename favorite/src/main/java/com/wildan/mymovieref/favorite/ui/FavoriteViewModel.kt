package com.wildan.mymovieref.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase

class FavoriteViewModel constructor(
    private val popularUseCase: PopularUseCase
) : ViewModel() {
    fun getFavoriteMovies(): LiveData<List<DetailPopularMovie?>> =
        popularUseCase.getFavoriteMovies().asLiveData()

    fun getFavoriteTVSeries(): LiveData<List<DetailPopularTVSeries?>> =
        popularUseCase.getFavoriteTVSeries().asLiveData()
}