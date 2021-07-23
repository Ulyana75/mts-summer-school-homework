package com.ulyanaab.mtshomework.recyclerView.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.ulyanaab.mtshomework.dto.MovieDto

class MoviesDiffUtilCallback(
    private val oldList: List<MovieDto>,
    private val newList: List<MovieDto>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}