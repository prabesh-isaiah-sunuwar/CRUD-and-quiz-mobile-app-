package com.example.as1

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.as1.databaseRelated.Note

data class NotesLists(
    //display a list of notes in the home page and used to stores the lists of notes
    val notes: List<Note> = emptyList(),
    // used to stores the notes title
    val title: MutableState<String> = mutableStateOf(""),
    //     used to stores the notes contents inside
    val contents: MutableState<String> = mutableStateOf("")
)
