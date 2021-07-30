package com.ulyanaab.mtshomework.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.GenreDto

class GenreAdapter(
    private var dataList: List<GenreDto>,
    private val listener: (item: GenreDto) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreHolder(view)
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        holder.textField.text = dataList[position].genre
        holder.itemView.setOnClickListener {
            listener(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(newList: List<GenreDto>) {
        dataList = newList
    }


    class GenreHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textField: TextView = view.findViewById(R.id.text_genre)
    }

}