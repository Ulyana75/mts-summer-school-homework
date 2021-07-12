package com.ulyanaab.mtshomework.recyclerView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OffsetDecorator(
    private val dpi: Int,
    private val left: Int = 0,
    private val top: Int = 0,
    private val right: Int = 0,
    private val bottom: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(convertToDp(left), convertToDp(top), convertToDp(right), convertToDp(bottom))
    }

    private fun convertToDp(value: Int): Int {
        return value * 160 / dpi
    }

}