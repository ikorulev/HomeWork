package ru.ikorulev.homework.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "films")

data class FilmDb (

    @PrimaryKey
    @ColumnInfo(name = "title")val filmTitle: String,
    @ColumnInfo(name = "path") val filmPath: String,
    @ColumnInfo(name = "details") val filmDetails: String

) {
    var isSelected: Boolean = false
    var isFavorite: Boolean = false
}