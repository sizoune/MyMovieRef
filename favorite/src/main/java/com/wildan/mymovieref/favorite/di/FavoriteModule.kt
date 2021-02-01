package com.wildan.mymovieref.favorite.di

import com.wildan.mymovieref.favorite.ui.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FavoriteModule {
    val favoriteViewModelModule = module {
        viewModel { FavoriteViewModel(get()) }
    }
}