package com.wildan.mymovieref.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wildan.mymovieref.R
import com.wildan.mymovieref.data.local.FavoriteMovies
import com.wildan.mymovieref.databinding.ItemListBinding
import com.wildan.mymovieref.utils.Constants

class FavoriteMovieAdapter(
    private val clickListener: (Int) -> Unit
) : PagedListAdapter<FavoriteMovies, FavoriteMovieAdapter.MovieHolder>(DIFF_CALLBACK) {

    class MovieHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bindItemtoView(
            data: FavoriteMovies,
            clickListener: (Int) -> Unit
        ) {
            with(containerView) {
                val binding = ItemListBinding.bind(this)

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
                    .load(Constants.IMAGE_PREFIX + data.poster_path)
                    .apply(
                        RequestOptions.placeholderOf(circularProgressDrawable)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.posterImage)

                setOnClickListener { clickListener(data.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        getItem(position)?.let { holder.bindItemtoView(it, clickListener) }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteMovies> =
            object : DiffUtil.ItemCallback<FavoriteMovies>() {
                override fun areItemsTheSame(
                    oldFav: FavoriteMovies,
                    newFav: FavoriteMovies
                ): Boolean {
                    return oldFav.id == newFav.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldFav: FavoriteMovies,
                    newFav: FavoriteMovies
                ): Boolean {
                    return oldFav == newFav
                }
            }
    }
}