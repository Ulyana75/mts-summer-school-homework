package com.ulyanaab.mtshomework

import android.content.Context

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