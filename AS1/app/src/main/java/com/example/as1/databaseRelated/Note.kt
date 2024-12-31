package com.example.as1.databaseRelated

import androidx.room.Entity
import androidx.room.PrimaryKey

//Declaration of the data class called Note
@Entity
data class Note(
    //these stores their respective attributes of the
    val title: String,
    val contents: String,
    val yoDateThatIsAdded: Long,

    @PrimaryKey(autoGenerate = true)
    //This separates each rows from everyone due to its uniqueness
    val id: Int = 0//tells the room that 0 is the PK
)
