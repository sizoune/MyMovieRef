package com.wildan.mymovieref.favorite.utils

import androidx.fragment.app.Fragment
import com.wildan.mymovieref.core.di.RepositoryModule
import dagger.hilt.android.EntryPointAccessors

fun Fragment.inject() {
    DaggerFavoriteFragmentComponent.factory().create(
        EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            RepositoryModule::class.java
        ),
        requireActivity().application
    )
        .inject(this)
}