package ru.ikorulev.homework.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmItem(

    @SerializedName("title") val filmTitle: String?,
    @SerializedName("backdrop_path") val filmImage: String?,
    @SerializedName("overview") val filmDetails: String?,


    var isSelected: Boolean,
    var isFavorite: Boolean

) : Parcelable