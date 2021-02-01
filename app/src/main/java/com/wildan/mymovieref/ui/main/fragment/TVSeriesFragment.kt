package com.wildan.mymovieref.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wildan.mymovieref.core.domain.model.PopularTVSeries
import com.wildan.mymovieref.core.ui.TVSeriesAdapter
import com.wildan.mymovieref.core.utils.*
import com.wildan.mymovieref.databinding.FragmentTVSeriesBinding
import com.wildan.mymovieref.ui.detail.DetailActivity
import com.wildan.mymovieref.ui.main.viewmodel.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class TVSeriesFragment : Fragment() {

    private val pageViewModel: MovieViewModel by viewModel()
    private var _binding: FragmentTVSeriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTVSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
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

}