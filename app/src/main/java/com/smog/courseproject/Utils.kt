package com.smog.courseproject

import android.content.Context

fun getDrawableFromName(context: Context, name:String?):Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}
