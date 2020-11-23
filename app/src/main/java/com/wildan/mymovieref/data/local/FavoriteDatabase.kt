package com.wildan.mymovieref.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteMovies::class, FavoriteTVSeries::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}