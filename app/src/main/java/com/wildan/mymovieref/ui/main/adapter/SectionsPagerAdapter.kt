package com.wildan.mymovieref.ui.main.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wildan.mymovieref.R
import com.wildan.mymovieref.ui.main.fragment.MovieFragment
import com.wildan.mymovieref.ui.main.fragment.TVSeriesFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

@Suppress("DEPRECATION")
class SectionsPagerAdapter(
    private val context: Context, fm: FragmentManager,
    private val isFavorite: Boolean
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment.newInstance(isFavorite)
            else -> TVSeriesFragment.newInstance(isFavorite)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}