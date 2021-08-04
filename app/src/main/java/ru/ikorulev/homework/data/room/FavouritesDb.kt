package ru.ikorulev.homework.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourites",
    indices = [
        Index(value = ["title"])
    ]
)

data class FavouritesDb (

    @PrimaryKey
    @ColumnInfo(name = "id")val filmId: Int,
    @ColumnInfo(name = "title")val filmTitle: String,
    @ColumnInfo(name = "path") val filmPath: String,

)