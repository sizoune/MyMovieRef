package com.wildan.mymovieref.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.wildan.mymovieref.data.local.FavoriteTVSeries
import com.wildan.mymovieref.data.model.PopularTVSeries
import com.wildan.mymovieref.databinding.FragmentTVSeriesBinding
import com.wildan.mymovieref.ui.detail.DetailActivity
import com.wildan.mymovieref.ui.main.adapter.FavoriteTVAdapter
import com.wildan.mymovieref.ui.main.adapter.TVSeriesAdapter
import com.wildan.mymovieref.ui.main.viewmodel.MovieViewModel
import com.wildan.mymovieref.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVSeriesFragment : Fragment() {

    private val pageViewModel: MovieViewModel by viewModels()
    private var _binding: FragmentTVSeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterFavorite: FavoriteTVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTVSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { fav ->
            if (fav.getBoolean(MovieFragment.ARGS)) {
                //favorite
                loadFavoriteTV()
            } else {
                //bukan favorite
                setupObserver()
            }
        }
    }

    private fun loadFavoriteTV() {
        showLoading(true)
        pageViewModel.getFavoriteTVSeries().observe(viewLifecycleOwner) { data ->
            showLoading(false)
            if (data != null) {
                if (data.isNotEmpty()) {
                    binding.txtEmpty.hide()
                    setDataIntoListFavorite(data)
                } else {
                    binding.listTV.hide()
                    binding.txtEmpty.show()
                }
            } else {
                binding.listTV.hide()
                binding.txtEmpty.show()
            }
        }
    }

    private fun setDataIntoListFavorite(data: PagedList<FavoriteTVSeries>) {
        adapterFavorite = FavoriteTVAdapter {
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
        adapterFavorite.submitList(data)
    }

    private fun setupObserver() {
        pageViewModel.getTVSeriesPopularList(1).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        showLoading(false)
                        resource.data?.let { popularData ->
                            setDataIntoList(popularData)
                        }
                    }
                    Status.ERROR_SERVER -> {
                        showLoading(false)
                        it.message?.let { pesan -> binding.constraintLayout.showRetrySnackbar(pesan) { setupObserver() } }
                    }
                    Status.ERROR_NETWORK -> {
                        showLoading(false)
                        it.message?.let { pesan -> binding.constraintLayout.showRetrySnackbar(pesan) { setupObserver() } }
                    }
                    Status.ERROR_UNKNOWN -> {
                        showLoading(false)
                        it.message?.let { pesan -> binding.constraintLayout.showRetrySnackbar(pesan) { setupObserver() } }
                    }
                    Status.LOADING -> {
                        showLoading(true)
                    }
                }
            }
        })
    }

    private fun setDataIntoList(data: List<PopularTVSeries>) {
        binding.listTV.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(ListSpacingDecoration(3, 8, true, 0))
            adapter = TVSeriesAdapter(data) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Constants.DATA, it)
                intent.putExtra(Constants.CATEGORY, Constants.TV_SERIES)
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS = "FAVORITE"

        @JvmStatic
        fun newInstance(isFavorite: Boolean): TVSeriesFragment {
            val tvFragment = TVSeriesFragment()
            val bundle = Bundle()
            bundle.putBoolean(ARGS, isFavorite)
            tvFragment.arguments = bundle
            return tvFragment
        }
    }
}