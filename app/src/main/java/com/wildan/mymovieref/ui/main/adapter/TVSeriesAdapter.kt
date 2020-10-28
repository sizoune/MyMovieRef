package com.wildan.mymovieref.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wildan.mymovieref.R
import com.wildan.mymovieref.data.model.PopularTVSeries
import com.wildan.mymovieref.databinding.ItemListBinding
import com.wildan.mymovieref.utils.Constants
import com.wildan.mymovieref.utils.formatToMMMddyyyy

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
//                binding.movieTitle.text = data.name
//                binding.releaseDate.text = data.firstAirDate.formatToMMMddyyyy("yyyy-MM-dd")
//                binding.userVote.text =
//                    context.getString(R.string.user_rating, data.voteAverage.toString())

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