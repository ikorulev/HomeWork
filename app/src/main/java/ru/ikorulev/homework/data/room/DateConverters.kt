package ru.ikorulev.homework.data.room

import androidx.room.TypeConverter
import java.util.*

class DateConverters {
    @TypeConverter
    fun fromWatchDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun ToWatchDate(date: Date?): Long? {
        return date?.time
    }
}