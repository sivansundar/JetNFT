package com.sivan.jetnft.util

import androidx.room.TypeConverter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeConverter {

    @TypeConverter
    fun fromZonedDateTimeToString(value : ZonedDateTime) : String {
        return value.toString()
    }

    @TypeConverter
    fun fromStringToZonedDateTime(value : String) : ZonedDateTime {
        return ZonedDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("Z")))
    }


}