package com.example.as1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.as1.databaseRelated.NoteDatabaseHandler
import com.example.as1.ui.theme.AS1Theme

class MainActivity : ComponentActivity() {
    //creation of the database
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            //NotesDatabase is inside NotesDatabase.kt
            NoteDatabaseHandler::class.java,
            "NotesKoDataHaru.db"
        ).build()
    }

    //creation of view model factory and instance
    private val viewModel by viewModels<NoteView> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun<N: ViewModel> create(modelClass: Class<N>): N {
                    return NoteView(database.dao) as N
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AS1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//This is where the navigation of pages occurs
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "Login"){
                        composable("Login"){
                            //if the login is successful, then, it will navigate to the NotesScreen which is the home page
                            LoginActivity(loginSuccessful = {
                                navController.navigate("NotesScreen") {
                                    //As we dont want stack of screens behind, we use popupto 0 to avoid muiltiple back button clicks
                                    popUpTo(0)
                                }
                            })
                        }
                        //This is the navigation to the home page from other pages
                        composable("NotesScreen"){
                            HomePageNoteScreen(
                                state = state,
                                navController = navController,
                                //use onEvent so we want to know when the user do the action
                                onClickEvent = viewModel::onEvent
                            )
                        }
                        //This is the navigation to the Add note page
                        composable("AddNoteScreen") {
                            AddingTheNoteToTheScreen(
                                state = state,
                                navController = navController,
                                //use onEvent so we want to know when the user do the action
                                onClickEvent = viewModel::onEvent
                            )
                        }
                        //This is the navigation to the update notes page
                        composable("UpdateNoteScreen") {
                            UpdatingTheNoteToTheScreen(
                                state = state,
                                navController = navController,
                                //use onEvent so we want to know when the user do the action
                                onClickEvent = viewModel::onEvent
                            )
                        }
                        //This is the navigation to the Quiz page from other pages
                        composable("QuizScreen"){
                            //QuizPreview is being called here for navigation
                            QuizPreview()
                        }

                    }


                }
            }
        }
    }
}




