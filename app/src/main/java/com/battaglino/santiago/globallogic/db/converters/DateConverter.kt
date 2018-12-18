package com.battaglino.santiago.globallogic.db.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Santiago Battaglino.
 */
class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}