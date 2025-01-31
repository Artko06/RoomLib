package com.example.roomlib

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ITelContactDao {

    @Upsert
    suspend fun upsertTelContact(telContact: TelContact)

    @Delete
    suspend fun deleteTelContact(telContact: TelContact)

    @Query("SELECT * FROM TelContact ORDER BY firstName")
    fun getTelContactOrderByFirstName(): Flow<List<TelContact>>

    @Query("SELECT * FROM TelContact ORDER BY lastName")
    fun getTelContactOrderByLastName(): Flow<List<TelContact>>

    @Query("SELECT * FROM TelContact ORDER BY telephoneNumber")
    fun getTelContactOrderByTelephoneNumber(): Flow<List<TelContact>>
}