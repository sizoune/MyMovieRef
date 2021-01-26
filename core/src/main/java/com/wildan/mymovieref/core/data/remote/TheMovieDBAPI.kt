package com.wildan.mymovieref.core.data.remote

import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.core.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ResponseListObject<PopularMovieResponse>, ErrorResponse>

    @GET("tv/popular")
    suspend fun getPopularTVSeries(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ResponseListObject<PopularTVSeriesResponse>, ErrorResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String
    ): NetworkResponse<DetailMovieResponse, ErrorResponse>

    @GET("tv/{tv_id}")
    suspend fun getDetailTV(
        @Path("tv_id") tvID: Int,
        @Query("api_key") apiKey: String
    ): NetworkResponse<DetailTVSeriesResponse, ErrorResponse>
}