package com.wildan.mymovieref

import android.app.Application
import com.wildan.mymovieref.core.di.CoreModule.databaseModule
import com.wildan.mymovieref.core.di.CoreModule.networkModule
import com.wildan.mymovieref.core.di.CoreModule.repositoryModule
import com.wildan.mymovieref.di.AppModule.useCaseModule
import com.wildan.mymovieref.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}