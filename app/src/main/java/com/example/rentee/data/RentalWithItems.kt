package com.example.rentee.data

import androidx.room.Embedded
import androidx.room.Relation

data class RentalWithItems(
    @Embedded val rental: Rental,
    @Relation(
        parentColumn = "rental_id",
        entityColumn = "item_id"
    )
    val items: List<Item>
)
