package com.example.as1.databaseRelated
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface NotessDao {
    //You can do either both update or insert with this function which also save time for the developer
    @Upsert
    //suspend helps make function asynchronous
    suspend fun upsertNote(note: Note)

    @Delete
    //suspend helps make function asynchronous for processing
    suspend fun deleteNote(note: Note)



    //This query will show you the data in according to the oldest note to the newest note at the bottom.
    @Query("SELECT * FROM note ORDER BY yoDateThatIsAdded")
    fun getNotesOrderedByAddedDate(): Flow<List<Note>>

    //This query will show you the data according to the alphabetical order from A to Z at the bottom
    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNotesOrderedByTitleAlphabetically(): Flow<List<Note>>


}
