package com.example.as1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.as1.databaseRelated.Note
import com.example.as1.databaseRelated.NotessDao

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteView(
    private val dao: NotessDao
): ViewModel() {
    //Whether our variable that tells us whether our notes are sorted by the date they were added
// or by the title
    //want notes to be the ordered by they were added not by the title until the user changes that
    //MutableStateFlow is used to track the sort state
    private val sortedOutByAddedDate = MutableStateFlow(true)
    //defining a variable latestNoteEdit which is to stores the latest Edited notes
    private var latestNoteEdit: Note? = null

    private var notes =
        //This fines a way to get the notes from the database which then sorts based on the type of the data and the date which is getNotesOrderedByAddedDate
        sortedOutByAddedDate.flatMapLatest { sort->
            if(sort){
                dao.getNotesOrderedByAddedDate()
            }else{
                dao.getNotesOrderedByTitleAlphabetically()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    //this MutableStateFlow(NotesLists())is used to track the NotesLists()
    val _stateOf = MutableStateFlow(NotesLists())
    val state =
        //if any changes happen in these 3 parameters, then the state needs to updated which is in the cruly brackets
        combine(_stateOf, sortedOutByAddedDate, notes) { state, isSortedByAddedDate, notes ->
            state.copy(
                //notes is equals to the new note
                notes = notes

            )

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000),NotesLists())



    fun onEvent(event: NoteOnClickEvent){
        when (event){
            //we want to know when the user delete the note , Save the note or Sort out the notes
            //launches a coroutine to delete the note
            is NoteOnClickEvent.DeleteNote -> {
                viewModelScope.launch{
                    //As deleteNote() was a suspend funtion, we launch the viewModelScope
                    dao.deleteNote(event.note)
                }
            }
//The .SaveNote helps in the creation of the note or an edit and then clears the input field once the add button or the update button is clicked.
            //The latest edited data will occur or if it is a new note then a new note data will be added.
            is NoteOnClickEvent.SaveNote -> {
                val note = latestNoteEdit?.copy(
                    title = state.value.title.value,
                    contents = state.value.contents.value,
                    yoDateThatIsAdded = System.currentTimeMillis()
                ) ?: Note(
                    title = state.value.title.value,
                    contents = state.value.contents.value,
                    yoDateThatIsAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _stateOf.update {
                    it.copy(
                        //set title and description to be an empty string again as we dont want our title and description to be filled with any value because it is already saved in the database
                        title = mutableStateOf(""),
                        contents = mutableStateOf("")

                    )
                }
                latestNoteEdit = null

            }
            //.UpdateNote is used to edit the current data into a new data
            is NoteOnClickEvent.UpdateNote -> {
                latestNoteEdit = event.note
                _stateOf.update {
                    it.copy(
                        title = mutableStateOf(event.note.title),
                        contents = mutableStateOf(event.note.contents)
                    )
                }
            }
            NoteOnClickEvent.SortNotes -> {
                //If sortedOutByAddedDate.value is true then it will sortedOutByAddedDate.value
                //if its false which is this "!sortedOutByAddedDate.value" then we sort it by title
                //meaning to say is , that is like a toggle button. They is 2 features in this button.
                sortedOutByAddedDate.value = !sortedOutByAddedDate.value

            }
        }

    }


}