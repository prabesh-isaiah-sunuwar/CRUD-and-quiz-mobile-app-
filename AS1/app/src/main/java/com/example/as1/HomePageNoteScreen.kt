package com.example.as1

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageNoteScreen(
    state: NotesLists,
    navController: NavController,
    onClickEvent: (NoteOnClickEvent) -> Unit
) {
//initialisation of navigationController,context and selected
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    //Usage of selected is for the icon colour.
    //if a profile screen is clicked, the selected icon which is the profile icon should be in white color and other should be in grey color
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    Scaffold(
        topBar = {
            TopAppBar(

                title = { Text(text = "My Notes", fontSize = 29.sp, fontWeight = FontWeight.SemiBold) },

                actions = {
                    //Call NotesEvent.SortNotes inside the lambda which is in NotesEvent
                    IconButton(onClick = { onClickEvent(NoteOnClickEvent.SortNotes)
                        Toast.makeText(context, "Sorts out the note from the oldest to the newest or Alphabetically", Toast.LENGTH_SHORT).show()}) {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Sort notes by title or date")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                //color of the bottom bar
                containerColor = Color(0xFFD0B98C)
            ) {
                Column (
                    //This aligns the icons as well as the related title together
                    horizontalAlignment = Alignment.CenterHorizontally

                ){

                    Text(text ="Home")
                    IconButton(
                        onClick = {
                            //Initially the selected value for the icon will be Home
                            selected.value = Icons.Default.Home
                            //As the user click on the Home icon,it will navigate using navController.navigate to the Home Screen
                            navController.navigate("NotesScreen") {
                                //As we dont want stack of screens behind, we use popupto 0 to avoid muiltiple back button clicks
                                popUpTo(0)

                            }
                        },
                        //All the icon equally takes place on the bottombar when using 1f
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            //tint means the color of the icon
                            //If the user is on home screen, then home icon will be white and others will be grey
                            tint = if (selected.value == Icons.Default.Home) Color.White else Color.Black
                        )

                    }

                }







                Box(
                    //Box is used to make the add note button in the middle
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                )

                {
                    //We use the FloatingActionButton to to highly emphasis this add button which is used to perform a primary action in an application
                    //Toast is used to check if the add button is working by sending "Open Bottom Sheet" message.
                    FloatingActionButton(onClick = {
                        Toast.makeText(context, "Add Some Note", Toast.LENGTH_SHORT).show()
                        //This state of the note of the fields is left empty for the user to enter thier desired words.
                        state.title.value = ""
                        state.contents.value = ""
                        //Navigates to the other screen which is AddNoteScreen
                        navController.navigate("AddNoteScreen")
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Additon of new note", tint = Color(0xff6600ff))
                    }
                }







                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)

                ) {
                    Text(text = "Quiz")
                    IconButton(
                        onClick = {
                            //Initially the selected value for the icon will be Search
                            selected.value = Icons.Default.PlayArrow

                            //As the user click on the Search icon,it will navigate using navigationController to the Search Screen
                            navController.navigate("QuizScreen")

                        },

                        //All the icon equally takes place on the bottombar when using 1f
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            //tint means the color of the icon
                            //If the user is on search screen, then search icon will be white and others will be grey
                            tint = if (selected.value == Icons.Default.PlayArrow) Color.White else Color.Black
                        )


                    }
                }
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)

                ) {
                    Text(text = "Logout")
                    IconButton(
                        onClick = {

                            //Initially the selected value for the icon will be Logout
                            selected.value = Icons.Default.ExitToApp
                            //As the user click btnLogoutLambda()on the Profile icon,it will navigate using navigationController to the Profile Screen
                            navController.navigate("Login") {
                                //As we dont want stack of screens behind, we use popupto 0 to avoid muiltiple back button clicks
                                popUpTo(0)


                            }
                        },
                        //All the icon equally separates on the bottombar when using 1f.
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            //tint means the color of the icon
                            //If the user is on home screen, then Profile icon will be white and others will be grey
                            tint = if (selected.value == Icons.Default.ExitToApp) Color.White else Color.Black

                        )
                    }

                }

            }
        }

    ) { paddingValues ->
//LazyColumn created inside the scaffold
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(19.dp)
        ) {

            items(state.notes.size) { index ->
                NoteAttributeItems(
                    state = state,
                    index = index,
                    onClickEvent = onClickEvent,
                    //You need navController to navigate from one page to another for the button to work like the edit button
                    navController = navController
                )
            }

        }



    }

}
//This fun is the attributes of what is in the card. There is the title, contents , edit button and delete button
@Composable
fun NoteAttributeItems(
    state: NotesLists,
    index: Int,
    onClickEvent: (NoteOnClickEvent) -> Unit,
    //You need navController to navigate from one page to another for the button to work like the edit button so you need to initiate it here
    navController: NavController
) {
    Card {
        Modifier.padding(20.dp)

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFF5F0E6))


        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(20.dp)


            ) {
                Text(
                    text = state.notes[index].title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,


                    )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = state.notes[index].contents,
                    fontSize = 12.sp,


                )

            }
            //Edit button
            IconButton(
                onClick = {
                    //NoteOnClickEvent is used to interate with the
                    onClickEvent(NoteOnClickEvent.UpdateNote(state.notes[index]))
                    //When the icon edit button is clicked, it leads to the UpdatingTheNoteToTheScreen.kt page for editing
                    navController.navigate("UpdateNoteScreen")
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edits the Note",
                    modifier = Modifier.size(29.dp),
                    tint = Color(0xFF665200)
                )

            }
                //Delete button
            IconButton(
                onClick = {
                    onClickEvent(NoteOnClickEvent.DeleteNote(state.notes[index]))
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Deletes the Note",
                    modifier = Modifier.size(29.dp),
                    tint = Color(0xFF665200)
                )

            }
        }

    }
}


