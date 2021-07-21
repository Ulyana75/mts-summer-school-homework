package com.ulyanaab.mtshomework.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.calculateImageSizeInPX
import com.ulyanaab.mtshomework.dto.MovieDto

class MoviesAdapter(
    private val dataList: List<MovieDto>,
    private val listener: (item: MovieDto) -> Unit,
    private val imgMetrics: Pair<Int, Int>?
) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        val holder = MoviesHolder(view)

        // to make images as big as possible with fixed offsets
        if (imgMetrics != null) {
            val layoutParams = holder.poster.layoutParams
            layoutParams.width = imgMetrics.first
            layoutParams.height = imgMetrics.second
            holder.poster.layoutParams = layoutParams
        }

        // to round corners of poster
        holder.poster.clipToOutline = true

        return MoviesHolder(view)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val item = dataList[position]
        holder.poster.load(item.imageUrl)
        holder.title.text = item.title
        holder.description.text = item.description
        holder.ageRestriction.text = "${item.ageRestriction}+"

        for (i in 0 until item.rateScore) {
            holder.rating[i].setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_full_star))
        }
        for (i in 4 downTo item.rateScore) {
            holder.rating[i].setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_empty_star))
        }

        holder.itemView.setOnClickListener {
            listener(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size

    class MoviesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.film_poster)
        val title: TextView = view.findViewById(R.id.film_title)
        val description: TextView = view.findViewById(R.id.film_description)
        val rating: List<ImageView> = listOf(
            view.findViewById(R.id.star_first),
            view.findViewById(R.id.star_second), view.findViewById(R.id.star_third),
            view.findViewById(R.id.star_fourth), view.findViewById(R.id.star_fifth)
        )
        val ageRestriction: TextView = view.findViewById(R.id.age_restriction)
    }

}