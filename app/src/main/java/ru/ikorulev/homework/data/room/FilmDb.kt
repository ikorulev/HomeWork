package ru.ikorulev.homework.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "films",
    indices = [
        Index(value = ["id"]),
        Index(value = ["sorting"])
    ]
)

data class FilmDb (

    @PrimaryKey
    @ColumnInfo(name = "id") val filmId: Int,
    @ColumnInfo(name = "title")val filmTitle: String,
    @ColumnInfo(name = "path") val filmPath: String,
    @ColumnInfo(name = "details") val filmDetails: String,
    @ColumnInfo(name = "sorting") val filmSorted: Int,

) {
    var isSelected: Boolean = false
    var isFavorite: Boolean = false
    var isWatchLater: Boolean = false
}