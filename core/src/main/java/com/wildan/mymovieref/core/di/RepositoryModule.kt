package com.wildan.mymovieref.core.di

import com.wildan.mymovieref.core.data.local.FavoriteDao
import com.wildan.mymovieref.core.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.core.data.repository.PopularRepository
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providePopularRepository(
        apiService: TheMovieDBAPI,
        favDao: FavoriteDao
    ): IPopularRepository = PopularRepository(apiService, favDao)
}