package com.wildan.mymovieref.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteMoviesEntity::class, FavoriteTVSeriesEntity::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}