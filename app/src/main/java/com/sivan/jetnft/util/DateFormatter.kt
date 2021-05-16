package com.sivan.jetnft.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*


fun ZonedDateTime?.toDateTimeString() : String {
    if (this!=null) {
        val zonedDateTime = ZonedDateTime.of(this.toLocalDateTime(), ZoneId.systemDefault())
        val date = zonedDateTime.toLocalDate()

        val dateString = "${date.dayOfMonth} ${date.month.toString().toLowerCase(Locale.ROOT)
            .capitalize(Locale.getDefault())}, ${date.year}"

        val time = this.toLocalTime().truncatedTo(ChronoUnit.MINUTES)

        return "${dateString} ${time}"
    }

    return ""
}