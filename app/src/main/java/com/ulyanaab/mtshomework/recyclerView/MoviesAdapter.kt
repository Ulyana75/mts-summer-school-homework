package com.ulyanaab.mtshomework.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ulyanaab.mtshomework.MAX_TITLE_LENGTH
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.dto.MovieDto

class MoviesAdapter(private val dataList: List<MovieDto>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesHolder(view)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val item = dataList[position]
        holder.poster.load(item.imageUrl)
        // to round corners of poster
        holder.poster.clipToOutline = true
        holder.title.text =
            if (item.title.length <= MAX_TITLE_LENGTH) item.title else item.title.substring(
                0,
                MAX_TITLE_LENGTH + 1
            )
        holder.description.text = item.description
        holder.ageRestriction.text = "${item.ageRestriction}+"

        for (i in 0 until item.rateScore) {
            holder.rating[i].setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_full_star))
        }
        for (i in 4 downTo item.rateScore) {
            holder.rating[i].setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_empty_star))
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