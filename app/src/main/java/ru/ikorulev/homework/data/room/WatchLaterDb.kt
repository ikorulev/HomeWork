package ru.ikorulev.homework.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "watch_later",
    indices = [
        Index(value = ["id"])
    ]
)

class WatchLaterDb(

    @PrimaryKey
    @ColumnInfo(name = "id") val filmId: Int,
    @ColumnInfo(name = "title") val filmTitle: String,
    @ColumnInfo(name = "path") val filmPath: String,
    @ColumnInfo(name = "details") val filmDetails: String,
    @ColumnInfo(name = "watch_date") val watchDate: Date

)