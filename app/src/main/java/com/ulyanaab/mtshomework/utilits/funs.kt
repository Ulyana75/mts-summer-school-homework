package com.ulyanaab.mtshomework.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ulyanaab.mtshomework.R

fun calculateImageSizeInPX(context: Context): Pair<Int, Int>? {
    val dpi = context.resources.displayMetrics.densityDpi
    val widthDp = convertToDP(context.resources.displayMetrics.widthPixels, dpi)
    val imgWidth = (widthDp - 60) / 2   // 60 - it's three offsets by 20dp, 2 - two columns
    val imgHeightInPx = convertToPX((imgWidth * 36 / 25.0).toInt(), dpi)   // width : height = 150 : 216 = 25 : 36
    if (imgHeightInPx * 3 > context.resources.displayMetrics.heightPixels) {   // if image becomes very huge - not resize it
        return null
    }
    return Pair(
        convertToPX(imgWidth, dpi),
        imgHeightInPx
    )
}

fun convertToDP(value: Int, dpi: Int): Int {
    return (value / (dpi / 160.0)).toInt()
}

fun convertToPX(value: Int, dpi: Int): Int {
    return (value * (dpi / 160.0)).toInt()
}

fun replaceFragment(activity: FragmentActivity, fragment: Fragment, addToBackStack: Boolean) {
    if(addToBackStack) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment)
            .addToBackStack(null)
            .commit()
    } else {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment)
            .commit()
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun setRating(rating: Int, view: View) {
    val context = view.context
    val starList: List<ImageView> = listOf(
        view.findViewById(R.id.star_first),
        view.findViewById(R.id.star_second), view.findViewById(R.id.star_third),
        view.findViewById(R.id.star_fourth), view.findViewById(R.id.star_fifth)
    )

    for (i in 0 until rating) {
        starList[i].setImageDrawable(context.getDrawable(R.drawable.ic_full_star))
    }
    for (i in 4 downTo rating) {
        starList[i].setImageDrawable(context.getDrawable(R.drawable.ic_empty_star))
    }
}

//TODO сделать актеров через ресайклер