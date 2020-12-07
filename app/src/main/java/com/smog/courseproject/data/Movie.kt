package com.smog.courseproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val posterLink:String? = null,
    val rating:String? = null,
    var genres:String? = null,
    val stars:Float = 0.0f,
    val reviewCount:Int = 0,
    val title:String? = null,
    val length:Int = 0,
    val isLiked:Boolean = false,
    val description:String? = null,
    val detailLink:String? = null,
):Parcelable