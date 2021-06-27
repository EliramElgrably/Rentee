package com.example.rentee.utilities

import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*


fun convertLDTToLong(localDateTime: LocalDateTime?):Long?
{
    val zoneId = ZoneId.systemDefault()
    return localDateTime?.atZone(zoneId)?.toInstant()?.toEpochMilli()
}

fun convertLongToLT(long: Long):LocalDate
{
    val zoneId = ZoneId.systemDefault()
    return Instant.ofEpochMilli(long).atZone(zoneId).toLocalDate()
}

fun formatLDoDateString(localDate: LocalDate?):String?{
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    return localDate?.format(formatter)
}

fun formatLToTimeString(localTime: LocalTime?):String?{
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    return localTime?.format(formatter)
}

val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

