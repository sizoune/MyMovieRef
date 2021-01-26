package com.wildan.mymovieref.favorite.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wildan.mymovieref.core.ui.FavoriteTVAdapter
import com.wildan.mymovieref.favorite.databinding.FragmentFavoriteSeriesBinding

class FavoriteSeriesFragment : Fragment() {

    private lateinit var adapterFavorite: FavoriteTVAdapter
    private var _binding: FragmentFavoriteSeriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

}