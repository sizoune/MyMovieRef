package com.wildan.mymovieref.ui.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.wildan.mymovieref.data.local.FavoriteMovies
import com.wildan.mymovieref.data.local.FavoriteTVSeries
import com.wildan.mymovieref.data.model.DetailMovie
import com.wildan.mymovieref.data.model.DetailTVSeries
import com.wildan.mymovieref.databinding.ActivityDetailBinding
import com.wildan.mymovieref.ui.detail.adapter.GenreAdapter
import com.wildan.mymovieref.ui.detail.viewmodel.DetailMovieViewModel
import com.wildan.mymovieref.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var detailMovie: DetailMovie
    private lateinit var detailTV: DetailTVSeries
    private lateinit var favMovie: FavoriteMovies
    private lateinit var favTV: FavoriteTVSeries
    private lateinit var myShareActionProvider: ShareActionProvider
    private var category: String? = null
    private var isFavorite = false

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
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
            val id = intent.getIntExtra(Constants.DATA, -1)
            checkFavState(id, category)
            btnFavorite.setOnClickListener {
                if (!isFavorite) {
                    // add to favorite
                    addToFav(id, category)
                } else {
                    //remove from favorite
                    removeFromFav(category)
                }
            }
        }
    }

    private fun addToFav(id: Int, category: String) {
        launch {
            if (category == Constants.MOVIE) {
                val favMovie = FavoriteMovies(
                    id,
                    detailMovie.backdropPath,
                    detailMovie.posterPath,
                    detailMovie.originalTitle,
                    detailMovie.releaseDate,
                    detailMovie.overview,
                    detailMovie.genres
                )
                viewModel.addFavMovie(favMovie)
            } else if (category == Constants.TV_SERIES) {
                val favTV = FavoriteTVSeries(
                    id,
                    detailTV.backdropPath,
                    detailTV.posterPath,
                    detailTV.originalName,
                    detailTV.firstAirDate,
                    detailTV.overview,
                    detailTV.genres
                )
                viewModel.addFavTV(favTV)
            }
        }
        isFavorite = true
        binding.parentLayout.showSnackbar("Berhasil ditambahkan ke favorite !")
    }

    private fun removeFromFav(category: String) {
        launch {
            if (category == Constants.MOVIE) {
                viewModel.deleteFavMovie(favMovie)
            } else if (category == Constants.TV_SERIES) {
                viewModel.deleteFavTV(favTV)
            }
        }
        isFavorite = false
        binding.parentLayout.showSnackbar("Berhasil dihapus dari favorite !")
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
            binding.btnFavorite.hide()
            binding.toolbarLayout.progressBar.show()
            binding.nestedContent.hide()
            binding.toolbarLayout.overlayColor.hide()
        } else {
            binding.btnFavorite.show()
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

    private fun checkFavState(id: Int, category: String) {
        showLoading(true)
        if (category == Constants.MOVIE) {
            viewModel.checkFavMovie(id).observe(this) { result ->
                if (result != null) {
                    isFavorite = true
                    bindFavoriteMovie(result)
                } else {
                    setupObserver(id, category)
                }
                setFavIconButton(isFavorite)
                showLoading(false)
            }
        } else if (category == Constants.TV_SERIES) {
            viewModel.checkFavTV(id).observe(this) { result ->
                if (result != null) {
                    isFavorite = true
                    bindFavoriteTV(result)
                } else {
                    setupObserver(id, category)
                }
                setFavIconButton(isFavorite)
                showLoading(false)
            }
        }
    }

    private fun bindFavoriteMovie(movie: FavoriteMovies) {
        favMovie = movie
        binding.posterImage.loadImageFromURL(movie.poster_path)
        binding.toolbarLayout.coverImage.loadImageFromURL(movie.backdrop_path)
        binding.movieTitle.text = movie.original_title
        binding.releaseDate.text = movie.release_date.formatToMMMddyyyy("yyy-MM-dd")
        binding.txtDesc.text = movie.overview
        binding.listProduction.adapter = GenreAdapter(movie.genres)

        changeShareIntent(shareData())
    }

    private fun bindFavoriteTV(movie: FavoriteTVSeries) {
        favTV = movie
        binding.posterImage.loadImageFromURL(movie.poster_path)
        binding.toolbarLayout.coverImage.loadImageFromURL(movie.backdrop_path)
        binding.movieTitle.text = movie.originalName
        binding.releaseDate.text = movie.firstAirDate.formatToMMMddyyyy("yyy-MM-dd")
        binding.txtDesc.text = movie.overview
        binding.listProduction.adapter = GenreAdapter(movie.genres)

        changeShareIntent(shareData())
    }

    private fun setFavIconButton(isFavorite: Boolean) {
        println(isFavorite)
        if (isFavorite) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmarks_24)
            if (drawable != null) {
                drawable.mutate()
                drawable.setColorFilter(ContextCompat.getColor(this, R.color.yellow_600))
                btnFavorite.setImageDrawable(drawable)
            }
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmarks_24)
            if (drawable != null) {
                drawable.mutate()
                drawable.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnFavorite.setImageDrawable(drawable)
            }
        }
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

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}