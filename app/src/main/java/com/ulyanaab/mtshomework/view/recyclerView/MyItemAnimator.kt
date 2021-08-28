package com.ulyanaab.mtshomework.view.recyclerView

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R

class MyItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder != null) {
            holder.itemView.animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.add_item_animation
            )
        }
        return super.animateAdd(holder)
    }

}