package ru.ikorulev.homework.data

import androidx.room.*

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: FavouritesDb)

    @Query("SELECT * FROM favourites")
    fun getAll(): List<FavouritesDb>

    //@Query("SELECT * FROM image WHERE id IN (:arg0)")
    //fun findByIds(imageTestIds: List<Int>): List<FilmDb>

    @Query("DELETE FROM favourites")
    fun deleteAll()

    @Delete
    fun delete(favourite: FavouritesDb)

}