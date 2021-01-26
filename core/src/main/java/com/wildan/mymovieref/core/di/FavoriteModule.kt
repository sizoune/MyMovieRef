package com.wildan.mymovieref.core.di

import com.wildan.mymovieref.core.data.repository.PopularRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FavoriteModule {
    fun exposePopularRepository(): PopularRepository
}