package com.example.roomlib

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TelContact::class],
    version = 1,
    exportSchema = false
)
abstract class TelContactDatabase: RoomDatabase() {
    abstract val dao: ITelContactDao
}