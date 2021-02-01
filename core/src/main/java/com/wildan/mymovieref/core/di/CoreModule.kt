package com.wildan.mymovieref.core.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.wildan.mymovieref.core.data.local.FavoriteDatabase
import com.wildan.mymovieref.core.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.core.data.repository.PopularRepository
import com.wildan.mymovieref.core.domain.repository.IPopularRepository
import com.wildan.mymovieref.core.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<FavoriteDatabase>().favoriteDao() }
        single {
            Room.databaseBuilder(
                androidContext(),
                FavoriteDatabase::class.java,
                "MyMovieRefDatabase"
            ).fallbackToDestructiveMigration().build()
        }
    }

    val networkModule = module {
        single {
            GsonBuilder().setLenient().create()
        }
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .client(get())
                .build()
            retrofit.create(TheMovieDBAPI::class.java)
        }
    }

    val repositoryModule = module {
        single<IPopularRepository> { PopularRepository(get(), get()) }
    }
}