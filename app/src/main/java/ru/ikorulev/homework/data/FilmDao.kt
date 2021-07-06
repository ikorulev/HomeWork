package ru.ikorulev.homework.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: List<FilmDb>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(film: FilmDb?)

    @Query("SELECT * FROM films")
    fun getAll(): List<FilmDb>

    @Query("SELECT * FROM films WHERE title = :search")
    fun findByTitle(search: String?): FilmDb?

    @Query("DELETE FROM films")
    fun deleteAll()

    @Delete
    fun delete(film: FilmDb)
}