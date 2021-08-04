package ru.ikorulev.homework.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")

data class FavouritesDb (

    @PrimaryKey
    @ColumnInfo(name = "title")val filmTitle: String,
    @ColumnInfo(name = "path") val filmPath: String,

)