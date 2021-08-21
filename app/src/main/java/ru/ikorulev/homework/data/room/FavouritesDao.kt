package ru.ikorulev.homework.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: FavouritesDb)

    @Query("SELECT * FROM favourites")
    fun getAll(): Flow<List<FavouritesDb>>

    @Query("SELECT * FROM favourites WHERE title = :search")
    fun findByTitle(search: String?): FavouritesDb?

    @Query("DELETE FROM favourites")
    fun deleteAll()

    @Delete
    fun delete(favourite: FavouritesDb)

}