package com.wildan.mymovieref.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wildan.mymovieref.R
import com.wildan.mymovieref.data.model.Genre
import com.wildan.mymovieref.databinding.ItemGenreBinding

class GenreAdapter(
    private val productions: List<Genre>
) : RecyclerView.Adapter<GenreAdapter.ProductionHolder>() {

    class ProductionHolder(private val containerView: View) :
        RecyclerView.ViewHolder(containerView) {
        fun bindItemtoView(
            data: Genre
        ) {
            with(containerView) {
                val binding = ItemGenreBinding.bind(this)
                binding.genreName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return ProductionHolder(view)
    }

    override fun onBindViewHolder(holder: ProductionHolder, position: Int) {
        holder.bindItemtoView(productions[position])
    }

    override fun getItemCount(): Int = productions.size

}