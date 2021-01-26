package com.wildan.mymovieref.favorite.di

import com.wildan.mymovieref.core.data.local.FavoriteDao
import com.wildan.mymovieref.core.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.core.data.repository.PopularRepository
import com.wildan.mymovieref.core.di.FragmentViewModelModule
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import com.wildan.mymovieref.favorite.ui.FavoriteViewModel_HiltModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module(includes = [ com.wildan.mymovieref.favorite.ui.FavoriteViewModel_HiltModule::class, FragmentViewModelModule::class])
@InstallIn(FragmentComponent::class)
abstract class FavoriteFragmentModule {
    companion object {
        @Provides
        fun providePopularRepository(
            apiService: TheMovieDBAPI,
            favDao: FavoriteDao
        ): IPopularRepository = PopularRepository(apiService, favDao)
    }
}