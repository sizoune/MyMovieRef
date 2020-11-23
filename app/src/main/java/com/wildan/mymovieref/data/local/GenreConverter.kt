package com.wildan.mymovieref.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wildan.mymovieref.data.model.Genre

class GenreConverter {

    @TypeConverter
    fun fromList(genres: List<Genre>): String {
        val gson = Gson()
        return gson.toJson(genres)
    }

    @TypeConverter
    fun fromString(genres: String): List<Genre> {
        val genresListType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genres, genresListType)
    }

}