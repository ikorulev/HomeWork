package ru.ikorulev.homework.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "films",
    indices = [
        Index(value = ["title"])
    ]
)

data class FilmDb (

    @ColumnInfo(name = "title")val filmTitle: String,
    @ColumnInfo(name = "path") val filmPath: String,
    @ColumnInfo(name = "details") val filmDetails: String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null

    var isSelected: Boolean = false
    var isFavorite: Boolean = false
}