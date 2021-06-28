package com.example.rentee.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithRentals(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_creator_id"
    )
    val rentals: List<Rental>
)
