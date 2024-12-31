package com.example.as1.databaseRelated


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1
)
//NoteDatabaseHandler class extends RoomDatabase() function
abstract class NoteDatabaseHandler: RoomDatabase(){
    //This can be used to access the dao using the interface NotessDao
    abstract val dao: NotessDao
}
