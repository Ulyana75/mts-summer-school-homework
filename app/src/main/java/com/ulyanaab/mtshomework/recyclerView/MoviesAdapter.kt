package com.ulyanaab.mtshomework.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.utilits.loadImageAsync
import com.ulyanaab.mtshomework.utilits.setRating

class MoviesAdapter(
    private var dataList: List<MovieDto>,
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
        holder.poster.loadImageAsync(item.imageUrl)
        holder.title.text = item.title
        holder.description.text = item.description
        holder.ageRestriction.text = "${item.ageRestriction}+"

        setRating(item.rateScore, holder.itemView)

        holder.itemView.setOnClickListener {
            listener(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(newData: List<MovieDto>) {
        dataList = newData
    }

    fun getData(): List<MovieDto> = dataList

    class MoviesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.film_poster)
        val title: TextView = view.findViewById(R.id.film_title)
        val description: TextView = view.findViewById(R.id.film_description)
        val ageRestriction: TextView = view.findViewById(R.id.age_restriction)
    }

}