package com.example.as1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun UpdatingTheNoteToTheScreen(
    //These declares a composable function in order to edit the required notes.
    state: NotesLists,
    navController: NavController,
    onClickEvent: (NoteOnClickEvent)-> Unit
) {


//This is the design of the edit page which includes the text fields , title and contents and the update button.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()

        ) {

            Text(text = "Update Note",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,

                )
            OutlinedTextField(
                value = state.title.value,
                onValueChange = { state.title.value = it},
                label = { Text("Title of your note") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value =  state.contents.value,
                onValueChange = { state.contents.value = it},//"it" is used so that the user can write in the textfield
                label = { Text("Write Something") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button( onClick = {
                onClickEvent(NoteOnClickEvent.SaveNote(
                    //title and description is in NoteState.kt
                    //title and description is in NoteState.kt where it is in used to store the list of nots ,title and contents
                    title = state.title.value,
                    contents = state.contents.value
                ))
                //This bring You back to the main home page after adding the note
                navController.popBackStack()},

                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFD0B98C),
                )){
                Text(text = "Update",
                    fontSize = 15.sp,
                    color = Color(0xFFf2f2f2))
            }



        }
    }
}