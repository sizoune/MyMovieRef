package com.wildan.mymovieref.favorite.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wildan.mymovieref.core.domain.model.DetailPopularTVSeries
import com.wildan.mymovieref.core.ui.FavoriteTVAdapter
import com.wildan.mymovieref.core.utils.Constants
import com.wildan.mymovieref.core.utils.ListSpacingDecoration
import com.wildan.mymovieref.core.utils.hide
import com.wildan.mymovieref.core.utils.show
import com.wildan.mymovieref.favorite.databinding.FragmentFavoriteSeriesBinding
import com.wildan.mymovieref.favorite.di.FavoriteModule
import com.wildan.mymovieref.favorite.ui.FavoriteViewModel
import com.wildan.mymovieref.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteSeriesFragment : Fragment() {

    private lateinit var adapterFavorite: FavoriteTVAdapter
    private var _binding: FragmentFavoriteSeriesBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(FavoriteModule.favoriteViewModelModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadFavoriteSeries()
    }

    private fun loadFavoriteSeries() {
        showLoading(true)
        favoriteViewModel.getFavoriteTVSeries().observe(viewLifecycleOwner,
            { data ->
                println("data ada ? $data")
                showLoading(false)
                if (data != null) {
                    if (data.isNotEmpty()) {
                        binding.txtEmpty.hide()
                        setDataIntoListFavorite(data as List<DetailPopularTVSeries>)
                    } else {
                        binding.listTV.hide()
                        binding.txtEmpty.show()
                    }
                } else {
                    binding.listTV.hide()
                    binding.txtEmpty.show()
                }
            }
        )
    }

    private fun setDataIntoListFavorite(data: List<DetailPopularTVSeries>) {
        adapterFavorite = FavoriteTVAdapter(data) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.DATA, it)
            intent.putExtra(Constants.CATEGORY, Constants.TV_SERIES)
            startActivity(intent)
        }
        binding.listTV.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(ListSpacingDecoration(3, 8, true, 0))
            adapter = adapterFavorite
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }

}