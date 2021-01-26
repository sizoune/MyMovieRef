package com.wildan.mymovieref.favorite.di

import android.app.Activity
import com.wildan.mymovieref.core.di.FavoriteModule
import com.wildan.mymovieref.core.di.RepositoryModule
import com.wildan.mymovieref.favorite.ui.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModule::class],
    modules = [FavoriteActivityModule::class]
)
interface FavoriteActivityComponent {

    fun inject(activity: FavoriteActivity)

    fun activity(): Activity

    @Component.Factory
    interface Factory {
        fun favoriteActivityComponent(
            @BindsInstance activity: Activity,
            favoriteModuleDependencies: FavoriteModule
        ): FavoriteActivityComponent
    }
}