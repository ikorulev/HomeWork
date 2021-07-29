package ru.ikorulev.homework.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(film: List<FilmDb>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(film: FilmDb?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(film: List<FilmDb>)

    @Query("SELECT * FROM films")
    fun getAll(): Flow<List<FilmDb>>

    @Query("SELECT * FROM films")
    fun getListAll(): List<FilmDb>

    @Query("SELECT * FROM films WHERE title = :search")
    fun findByTitle(search: String?): FilmDb?

    @Query("DELETE FROM films")
    fun deleteAll()

    @Delete
    fun delete(film: FilmDb)
}