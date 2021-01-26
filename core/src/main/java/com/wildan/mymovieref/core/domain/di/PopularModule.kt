package com.wildan.mymovieref.core.domain.di

import com.wildan.mymovieref.core.data.repository.PopularRepository
import com.wildan.mymovieref.core.domain.usecase.PopularInteractor
import com.wildan.mymovieref.core.domain.usecase.PopularUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class PopularModule {
    @Provides
    fun providePopularUseCase(popularRepository: PopularRepository): PopularUseCase =
        PopularInteractor(popularRepository)

}

