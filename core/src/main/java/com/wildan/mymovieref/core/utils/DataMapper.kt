package com.wildan.mymovieref.core.utils

import com.wildan.mymovieref.core.data.local.FavoriteMoviesEntity
import com.wildan.mymovieref.core.data.local.FavoriteTVSeriesEntity
import com.wildan.mymovieref.core.data.model.*
import com.wildan.mymovieref.core.domain.model.*

object DataMapper {

    private fun mapGenresResponseToDomain(input: List<GenreResponse>): List<Genre> =
        input.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }

    private fun mapGenresDomainToFavorite(input: List<Genre>): List<GenreResponse> =
        input.map {
            GenreResponse(
                id = it.id,
                name = it.name
            )
        }

    fun mapPopularMovieResponseToDomain(input: List<PopularMovieResponse>): List<PopularMovie> =
        input.map {
            PopularMovie(
                id = it.id,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                originalTitle = it.originalTitle,
                releaseDate = it.releaseDate,
                overview = it.overview
            )
        }

    fun mapPopularSeriesResponseToDomain(input: List<PopularTVSeriesResponse>): List<PopularTVSeries> =
        input.map {
            PopularTVSeries(
                id = it.id,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                originalName = it.originalName,
                overview = it.overview
            )
        }

    fun mapDetailMovieResponseToDomain(input: DetailMovieResponse): DetailPopularMovie =
        DetailPopularMovie(
            id = input.id,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            originalTitle = input.originalTitle,
            releaseDate = input.releaseDate,
            overview = input.overview,
            genres = mapGenresResponseToDomain(input.genres)
        )

    fun mapDetailSeriesResponseToDomain(input: DetailTVSeriesResponse): DetailPopularTVSeries =
        DetailPopularTVSeries(
            id = input.id,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            firstAirDate = input.firstAirDate,
            originalName = input.originalName,
            overview = input.overview,
            genres = mapGenresResponseToDomain(input.genres)
        )

    fun mapDetailMovieToFavorite(input: DetailPopularMovie): FavoriteMoviesEntity =
        FavoriteMoviesEntity(
            id = input.id,
            backdrop_path = input.backdropPath,
            poster_path = input.posterPath,
            release_date = input.releaseDate,
            original_title = input.originalTitle,
            overview = input.overview,
            genres = mapGenresDomainToFavorite(input.genres)
        )

    fun mapDetailSeriesToFavorite(input: DetailPopularTVSeries): FavoriteTVSeriesEntity =
        FavoriteTVSeriesEntity(
            id = input.id,
            backdrop_path = input.backdropPath,
            poster_path = input.posterPath,
            firstAirDate = input.firstAirDate,
            originalName = input.originalName,
            overview = input.overview,
            genres = mapGenresDomainToFavorite(input.genres)
        )

    fun mapDetailMovieFavoriteToDomain(input: List<FavoriteMoviesEntity>): List<DetailPopularMovie?> =
        input.map {
            mapDetailMoviesFavoriteToDomain(it)
        }

    fun mapDetailTVFavoriteToDomain(input: List<FavoriteTVSeriesEntity>): List<DetailPopularTVSeries?> =
        input.map {
            mapDetailSeriesFavoriteToDomain(it)
        }

    fun mapDetailMoviesFavoriteToDomain(data: FavoriteMoviesEntity?): DetailPopularMovie? =
        data?.let { input ->
            DetailPopularMovie(
                id = input.id,
                backdropPath = input.backdrop_path,
                posterPath = input.poster_path,
                releaseDate = input.release_date,
                originalTitle = input.original_title,
                overview = input.overview,
                genres = mapGenresResponseToDomain(input.genres)
            )
        }


    fun mapDetailSeriesFavoriteToDomain(data: FavoriteTVSeriesEntity?): DetailPopularTVSeries? =
        data?.let { input ->
            DetailPopularTVSeries(
                id = input.id,
                backdropPath = input.backdrop_path,
                posterPath = input.poster_path,
                firstAirDate = input.firstAirDate,
                originalName = input.originalName,
                overview = input.overview,
                genres = mapGenresResponseToDomain(input.genres)
            )
        }
}