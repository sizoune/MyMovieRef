package com.wildan.mymovieref.favorite.di

import com.wildan.mymovieref.core.data.local.FavoriteDao
import com.wildan.mymovieref.core.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.core.data.repository.PopularRepository
import com.wildan.mymovieref.core.di.DatabaseModule
import com.wildan.mymovieref.core.di.RetrofitModule
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module(includes = [RetrofitModule::class, DatabaseModule::class])
@InstallIn(ActivityComponent::class)
abstract class FavoriteActivityModule {
    companion object {
        @Provides
        fun providePopularRepository(
            apiService: TheMovieDBAPI,
            favDao: FavoriteDao
        ): IPopularRepository = PopularRepository(apiService, favDao)
    }
}