package com.example.roomlib

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TelContact")
data class TelContact(
    val firstName: String,
    val lastName: String,
    val telephoneNumber: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
