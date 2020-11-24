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
import com.wildan.mymovieref.data.local.FavoriteTVSeries
import com.wildan.mymovieref.databinding.ItemListBinding
import com.wildan.mymovieref.utils.Constants

class FavoriteTVAdapter(
    private val clickListener: (Int) -> Unit
) : PagedListAdapter<FavoriteTVSeries, FavoriteTVAdapter.MovieHolder>(DIFF_CALLBACK) {

    class MovieHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bindItemtoView(
            data: FavoriteTVSeries,
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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteTVSeries> =
            object : DiffUtil.ItemCallback<FavoriteTVSeries>() {
                override fun areItemsTheSame(
                    oldFav: FavoriteTVSeries,
                    newFav: FavoriteTVSeries
                ): Boolean {
                    return oldFav.id == newFav.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldFav: FavoriteTVSeries,
                    newFav: FavoriteTVSeries
                ): Boolean {
                    return oldFav == newFav
                }
            }
    }
}