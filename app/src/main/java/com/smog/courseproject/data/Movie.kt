package com.smog.courseproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val posterLink:String,
    val rating:String,
    var genres:String,
    val stars:Float = 0.0f,
    val reviewCount:Int = 0,
    val title:String,
    val length:Int = 0,
    val isLiked:Boolean = false,
    val description:String,
    val detailLink:String? = null,
):Parcelable