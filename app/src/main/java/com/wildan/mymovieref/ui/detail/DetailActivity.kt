package com.wildan.mymovieref.ui.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wildan.mymovieref.R
import com.wildan.mymovieref.data.model.DetailMovie
import com.wildan.mymovieref.data.model.DetailTVSeries
import com.wildan.mymovieref.databinding.ActivityDetailBinding
import com.wildan.mymovieref.ui.detail.adapter.GenreAdapter
import com.wildan.mymovieref.ui.detail.viewmodel.DetailMovieViewModel
import com.wildan.mymovieref.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var detailMovie: DetailMovie
    private lateinit var detailTV: DetailTVSeries
    private lateinit var myShareActionProvider: ShareActionProvider
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sharing_menu, menu)
        menu?.let {
            val shareItem = menu.findItem(R.id.menuShare)
            myShareActionProvider =
                MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
            setData()
            myShareActionProvider.setShareIntent(shareData())
        }
        return true
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        binding.listProduction.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setData() {
        category = intent.getStringExtra(Constants.CATEGORY)
        category?.let { category ->
            val movieID = intent.getIntExtra(Constants.DATA, -1)
            setupObserver(movieID, category)
        }
    }

    private fun setupObserver(id: Int, category: String) {
        if (category == Constants.MOVIE) {
            viewModel.getDetailMovie(id).observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            showLoading(false)
                            resource.data?.let { detailMovie ->
                                bindDetailMovie(detailMovie)
                            }
                        }
                        Status.ERROR_SERVER -> {
                            showLoading(false)
                            it.message?.let { pesan ->
                                binding.parentLayout.showRetrySnackbar(pesan) {
                                    setupObserver(
                                        id,
                                        category
                                    )
                                }
                            }
                        }
                        Status.ERROR_NETWORK -> {
                            showLoading(false)
                            it.message?.let { pesan ->
                                binding.parentLayout.showRetrySnackbar(pesan) {
                                    setupObserver(
                                        id,
                                        category
                                    )
                                }
                            }
                        }
                        Status.ERROR_UNKNOWN -> {
                            showLoading(false)
                            it.message?.let { pesan ->
                                binding.parentLayout.showRetrySnackbar(pesan) {
                                    setupObserver(
                                        id,
                                        category
                                    )
                                }
                            }
                        }
                        Status.LOADING -> {
                            showLoading(true)
                        }
                    }
                }
            })
        } else if (category == Constants.TV_SERIES) {
            viewModel.getDetailTV(id).observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            showLoading(false)
                            resource.data?.let { detailMovie ->
                                bindDetailTV(detailMovie)
                            }
                        }
                        Status.ERROR_SERVER -> {
                            showLoading(false)
                            it.message?.let { pesan ->
                                binding.parentLayout.showRetrySnackbar(pesan) {
                                    setupObserver(
                                        id,
                                        category
                                    )
                                }
                            }
                        }
                        Status.ERROR_NETWORK -> {
                            showLoading(false)
                            it.message?.let { pesan ->
                                binding.parentLayout.showRetrySnackbar(pesan) {
                                    setupObserver(
                                        id,
                                        category
                                    )
                                }
                            }
                        }
                        Status.ERROR_UNKNOWN -> {
                            showLoading(false)
                            it.message?.let { pesan ->
                                binding.parentLayout.showRetrySnackbar(pesan) {
                                    setupObserver(
                                        id,
                                        category
                                    )
                                }
                            }
                        }
                        Status.LOADING -> {
                            showLoading(true)
                        }
                    }
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.toolbarLayout.progressBar.show()
            binding.nestedContent.hide()
            binding.toolbarLayout.overlayColor.hide()
        } else {
            binding.toolbarLayout.progressBar.hide()
            binding.nestedContent.show()
            binding.toolbarLayout.overlayColor.show()
        }
    }

    private fun bindDetailMovie(movie: DetailMovie) {
        detailMovie = movie
        binding.posterImage.loadImageFromURL(movie.posterPath)
        binding.toolbarLayout.coverImage.loadImageFromURL(movie.backdropPath)
        binding.movieTitle.text = movie.title
        binding.releaseDate.text = movie.releaseDate.formatToMMMddyyyy("yyy-MM-dd")
        binding.txtDesc.text = movie.overview
        binding.listProduction.adapter = GenreAdapter(movie.genres)

        changeShareIntent(shareData())
    }

    private fun bindDetailTV(tvSeries: DetailTVSeries) {
        detailTV = tvSeries
        binding.posterImage.loadImageFromURL(tvSeries.posterPath)
        binding.toolbarLayout.coverImage.loadImageFromURL(tvSeries.backdropPath)
        binding.movieTitle.text = tvSeries.name
        binding.releaseDate.text = tvSeries.firstAirDate.formatToMMMddyyyy("yyy-MM-dd")
        binding.txtDesc.text = tvSeries.overview
        binding.listProduction.adapter = GenreAdapter(tvSeries.genres)

        changeShareIntent(shareData())
    }

    private fun shareData(): Intent? {
        if (::detailMovie.isInitialized || ::detailTV.isInitialized) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            if (category == Constants.MOVIE)
                shareIntent.putExtra(Intent.EXTRA_TEXT, detailMovie.overview)
            else
                shareIntent.putExtra(Intent.EXTRA_TEXT, detailTV.overview)
            return shareIntent
        }

        return null
    }

    private fun changeShareIntent(shareIntent: Intent?) {
        myShareActionProvider.setShareIntent(shareIntent)
    }

    private fun ImageView.loadImageFromURL(url: String) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(
            ContextCompat.getColor(
                context,
                R.color.red_A100
            )
        )
        circularProgressDrawable.start()
        Glide.with(context)
            .load(Constants.IMAGE_PREFIX + url)
            .apply(
                RequestOptions.placeholderOf(circularProgressDrawable)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}