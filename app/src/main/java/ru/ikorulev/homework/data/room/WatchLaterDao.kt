package ru.ikorulev.homework.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchLaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: WatchLaterDb)

    @Query("SELECT * FROM watch_later")
    fun getAll(): Flow<List<WatchLaterDb>>

    @Query("SELECT * FROM watch_later")
    fun getListAll(): List<WatchLaterDb>

    @Query("SELECT * FROM watch_later WHERE title = :search")
    fun findByTitle(search: String?): WatchLaterDb?

    @Query("DELETE FROM watch_later")
    fun deleteAll()

    @Delete
    fun delete(watchLater: WatchLaterDb)
}