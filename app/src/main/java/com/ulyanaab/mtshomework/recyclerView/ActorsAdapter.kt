package com.ulyanaab.mtshomework.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.dto.ActorDto
import com.ulyanaab.mtshomework.utilits.loadImageAsync

class ActorsAdapter(private val dataList: MutableList<ActorDto>) :
    RecyclerView.Adapter<ActorsAdapter.ActorsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        return ActorsHolder(view)
    }

    override fun onBindViewHolder(holder: ActorsHolder, position: Int) {
        holder.photo.loadImageAsync(dataList[position].drawable)
        holder.name.text = dataList[position].name
    }

    override fun getItemCount(): Int = dataList.size


    class ActorsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.actor_photo)
        val name: TextView = view.findViewById(R.id.actor_name)
    }

}