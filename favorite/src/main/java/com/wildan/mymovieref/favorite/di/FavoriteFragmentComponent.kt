package com.wildan.mymovieref.favorite.di

import androidx.fragment.app.Fragment
import com.wildan.mymovieref.core.di.FavoriteModule
import com.wildan.mymovieref.core.di.RepositoryModule
import com.wildan.mymovieref.favorite.ui.fragment.FavoriteMovieFragment
import com.wildan.mymovieref.favorite.ui.fragment.FavoriteSeriesFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModule::class],
    modules = [FavoriteActivityModule::class]
)
interface FavoriteFragmentComponent {

    fun inject(fragment: FavoriteMovieFragment)

    fun inject(fragment: FavoriteSeriesFragment)

    fun fragment(): Fragment

    @Component.Factory
    interface Factory {
        fun favoriteFragmentComponent(
            @BindsInstance fragment: Fragment,
            favoriteModuleDependencies: FavoriteModule
        ): FavoriteFragmentComponent
    }
}