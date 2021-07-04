package com.example.rentee.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Rent class.
 */
@Dao
interface RentalDao {
    @Query("SELECT * FROM rentals ORDER BY start_date_Time")
    fun getRentals(): Flow<List<Rental>>

    @Query("SELECT * FROM rentals WHERE rental_id = :rentalId")
    fun getRentalById(rentalId: String): Flow<Rental>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Rental>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rental: Rental)

    @Query("DELETE FROM rentals")
    suspend fun deleteAllRentals()

    @Query("DELETE FROM rentals WHERE rental_id = :rentalId")
    suspend fun deleteRentalById(rentalId: String)

    @Delete
    suspend fun deleteRental(rental: Rental)

    @Transaction
    @Query("SELECT * FROM rentals")
    fun getRentalWithItems(): List<RentalWithItems>
}