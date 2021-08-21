package ru.ikorulev.homework.data.room

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(film: List<FilmDb>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(film: FilmDb?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(film: List<FilmDb>)

    @Query("SELECT * FROM films")
    fun getAll(): Flowable<List<FilmDb>>

    @Query("SELECT * FROM films")
    fun getListAll(): Single<List<FilmDb>>

    @Query("SELECT * FROM films WHERE id = :search")
    fun findById(search: Int): FilmDb?

    @Query("SELECT * FROM films WHERE title = :search")
    fun findByTitle(search: String?): FilmDb?

    @Query("DELETE FROM films")
    fun deleteAll()

    @Delete
    fun delete(film: FilmDb)
}