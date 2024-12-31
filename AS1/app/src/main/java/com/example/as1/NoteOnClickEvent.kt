package com.example.as1

import com.example.as1.databaseRelated.Note

//This NotesEvent is for when a button is clicked.
//This is the declaration of the sealed interface named NoteOnClickEvent
sealed interface NoteOnClickEvent {
    // user can use this property sort out the notes
    object SortNotes: NoteOnClickEvent

    // user can use this property to delete a note
    data class DeleteNote(val note: Note): NoteOnClickEvent


    // user can use this property to edit a note
    data class UpdateNote(val note: Note): NoteOnClickEvent

    //User use this property to receive note
    data class SaveNote(
        val title: String,
        val contents: String
    ): NoteOnClickEvent

}