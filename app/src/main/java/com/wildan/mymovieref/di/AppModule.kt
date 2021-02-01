package com.wildan.mymovieref.di

import com.wildan.mymovieref.core.domain.usecase.PopularInteractor
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase
import com.wildan.mymovieref.ui.detail.viewmodel.DetailMovieViewModel
import com.wildan.mymovieref.ui.main.viewmodel.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<PopularUseCase> { PopularInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { MovieViewModel(get()) }
        viewModel { DetailMovieViewModel(get()) }
    }
}