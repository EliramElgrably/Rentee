package com.example.rentee.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "rentals")
data class Rental(
    @PrimaryKey
    @ColumnInfo(name = "rental_id") val rentalId: String?,
    @ColumnInfo(name = "user_creator_id") val userCreatorId: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "start_date_Time") val startDateTime: LocalDateTime?,
    @ColumnInfo(name = "end_date_Time") val endDateTime: LocalDateTime?,
    @Ignore val startDate: LocalDate?,
    @Ignore val endDate: LocalDate?,
    @Ignore val startTime: LocalTime?,
    @Ignore val endTime: LocalTime?
)
