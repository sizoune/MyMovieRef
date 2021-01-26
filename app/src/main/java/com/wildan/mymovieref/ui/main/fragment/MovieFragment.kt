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
import com.wildan.mymovieref.databinding.FragmentMovieBinding
import com.wildan.mymovieref.core.domain.model.DetailPopularMovie
import com.wildan.mymovieref.core.domain.model.PopularMovie
import com.wildan.mymovieref.ui.detail.DetailActivity
import com.wildan.mymovieref.core.ui.FavoriteMovieAdapter
import com.wildan.mymovieref.core.ui.MovieAdapter
import com.wildan.mymovieref.ui.main.viewmodel.MovieViewModel
import com.wildan.mymovieref.core.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val pageViewModel: MovieViewModel by viewModels()
    private lateinit var adapterFavorite: FavoriteMovieAdapter
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { fav ->
            if (fav.getBoolean(ARGS)) {
                //favorite
                loadFavoriteMovies()
            } else {
                //bukan favorite
                setupObserver()
            }
        }
    }


    private fun loadFavoriteMovies() {

        showLoading(true)
        pageViewModel.getFavoriteMovies().observe(viewLifecycleOwner,
            { data ->
                println("data ada ? $data")
                showLoading(false)
                if (data != null) {
                    if (data.isNotEmpty()) {
                        binding.txtEmpty.hide()
                        setDataIntoListFavorite(data)
                    } else {
                        binding.listMovie.hide()
                        binding.txtEmpty.show()
                    }
                } else {
                    binding.listMovie.hide()
                    binding.txtEmpty.show()
                }
            }
        )
    }

    private fun setupObserver() {
        pageViewModel.getMoviePopularList(1).observe(viewLifecycleOwner, {
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

    private fun setDataIntoListFavorite(data: PagedList<DetailPopularMovie>) {
        adapterFavorite = FavoriteMovieAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.DATA, it)
            intent.putExtra(Constants.CATEGORY, Constants.MOVIE)
            startActivity(intent)
        }
        binding.listMovie.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(ListSpacingDecoration(3, 8, true, 0))
            adapter = adapterFavorite
        }
        adapterFavorite.submitList(data)
    }

    private fun setDataIntoList(data: List<PopularMovie>) {
        binding.listMovie.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(ListSpacingDecoration(3, 8, true, 0))
            adapter = MovieAdapter(data) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Constants.DATA, it)
                intent.putExtra(Constants.CATEGORY, Constants.MOVIE)
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
        const val ARGS = "FAVORITE"

        @JvmStatic
        fun newInstance(isFavorite: Boolean): MovieFragment {
            val movieFragment = MovieFragment()
            val bundle = Bundle()
            bundle.putBoolean(ARGS, isFavorite)
            movieFragment.arguments = bundle
            return movieFragment
        }
    }
}