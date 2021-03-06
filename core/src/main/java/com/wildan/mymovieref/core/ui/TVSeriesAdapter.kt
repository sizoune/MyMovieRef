package com.wildan.mymovieref.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wildan.mymovieref.core.R
import com.wildan.mymovieref.core.databinding.ItemListBinding
import com.wildan.mymovieref.core.domain.model.PopularTVSeries
import com.wildan.mymovieref.core.utils.Constants

class TVSeriesAdapter(
    private val movies: List<PopularTVSeries>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<TVSeriesAdapter.MovieHolder>() {

    class MovieHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bindItemtoView(
            data: PopularTVSeries,
            clickListener: (Int) -> Unit
        ) {
            with(containerView) {
                val binding = ItemListBinding.bind(this)

                val circularProgressDrawable = CircularProgressDrawable(context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                Glide.with(context)
                    .load(Constants.IMAGE_PREFIX + data.posterPath)
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
        holder.bindItemtoView(movies[position], clickListener)
    }

    override fun getItemCount(): Int = movies.size
}