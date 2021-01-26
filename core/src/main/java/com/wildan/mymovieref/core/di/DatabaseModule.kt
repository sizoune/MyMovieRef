package com.wildan.mymovieref.core.di

import android.content.Context
import androidx.room.Room
import com.wildan.mymovieref.core.data.local.FavoriteDao
import com.wildan.mymovieref.core.data.local.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    fun provideFavoritesDao(appDatabase: FavoriteDatabase): FavoriteDao {
        return appDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoriteDatabase::class.java,
            "MyMovieRefDatabase"
        ).build()
    }
}