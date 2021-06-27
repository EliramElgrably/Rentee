package com.example.rentee.data

import androidx.room.Embedded
import androidx.room.Relation

data class RentalWithItems(
    @Embedded val rental: Rental,
    @Relation(
        parentColumn = "rental_id",
        entityColumn = "rental_related_id"
    )
    val items: List<Item>
)
