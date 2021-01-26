package com.wildan.mymovieref.favorite.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wildan.mymovieref.favorite.R
import com.wildan.mymovieref.favorite.ui.fragment.FavoriteMovieFragment
import com.wildan.mymovieref.favorite.ui.fragment.FavoriteSeriesFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

@Suppress("DEPRECATION")
class ViewPagerAdapter(
    private val context: Context, fm: FragmentManager
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment()
            else -> FavoriteSeriesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}