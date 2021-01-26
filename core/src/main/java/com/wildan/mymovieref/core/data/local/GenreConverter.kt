package com.wildan.mymovieref.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wildan.mymovieref.core.data.model.GenreResponse

class GenreConverter {

    @TypeConverter
    fun fromList(genres: List<GenreResponse>): String {
        val gson = Gson()
        return gson.toJson(genres)
    }

    @TypeConverter
    fun fromString(genres: String): List<GenreResponse> {
        val genresListType = object : TypeToken<List<GenreResponse>>() {}.type
        return Gson().fromJson(genres, genresListType)
    }

}