package com.example.as1

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizPreview() {
    //These are the questions for the quiz
    val quizQuestions = listOf(
        "Which of these animals are nocturnal?",
        "Which group of species are frog called as?",
        "What do you call a group of owl?"
    )
    //These are the options for the questions
    val mC = listOf(
        listOf("A raccoon","A dog","Bees","A Lion"),
        listOf("Mammals","Reptiles","Amphibians","Fish"),
        listOf("a Flock","a Parliament","a Pack","a school")

    )
    //These are the list of correct answers for the questions
    val rightAns = listOf(0,2,1)
    //The variable names below are mutable state are managed using the "remember" and "mutableStateOf"
//When starting a game the question should start with the 1st question.
    //The indicationOfOngoingQuestion which means the present question has an Int value
    var indicationOfOngoingQuestion by remember { mutableIntStateOf(0) }
    //Total points should start with 0 in the beginning of the game.
    //The totalTally has an Int value
    var totalTally by remember { mutableIntStateOf(0) }
    //Beginning of the game should start with false because the game has just started.
    var quizEnd by remember { mutableStateOf(false) }


    //we need the button colours to be the same in the start to be the same to not be confused with the correct or wrong button color.
    //This storages the list of colours in the variable
    var colourOfTheButton by remember { mutableStateOf(listOf(Color.DarkGray, Color.DarkGray, Color.DarkGray,Color.DarkGray)) }

    //This is for making the toast. We need the context which means the situation of the code to make the toast.
    val context = LocalContext.current
QuizUI(
    quizQuestions = quizQuestions,
    mC = mC,
    rightAns = rightAns,
    indicationOfOngoingQuestion = indicationOfOngoingQuestion,
    totalTally = totalTally,
    quizEnd = quizEnd,
    colourOfTheButton = colourOfTheButton,
    selectedAns = {indicationOfQuestion, indicationOfAns ->
        colourOfTheButton =colourOfTheButton.mapIndexed{ indication,_ ->
            when {
                //If the current button "indication" match with the button that was clicked "indicationOfAns" and the if the button that was clicked and the ongoing question matches then it will give a green button to that question.
                indication == indicationOfAns && indicationOfAns == rightAns[indicationOfQuestion] -> Color(0xff269900)
                //if the current button and the button that was clicked doesnt match, then it will give a red colour button telling that the question is wrong.
                indication == indicationOfAns -> Color(0xffcc0000)
                //if the current button and the button that was clicked match, then it will give a green colour button telling that the question is right.
                indication == rightAns[indicationOfQuestion] -> Color(0xff269900)
                //this is like the neutral colour which indicates that it is a button that was not clicked during the green or red button action phase.
                else -> Color.DarkGray
            }
        }
        //We use coroutine to delay the transition to the next question
        //Dispatchers.Main interacts with the UI
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            //if the 'indicationOfAns' which is the selected answer is right,then the totalTally will increase by 1
            if (indicationOfAns == rightAns[indicationOfQuestion]) {
                totalTally++
            }
            //This moves on to the next question
            //if the current ongoing question is not final,'indicationOfOngoingQuestion' will be incremented to go to the next question
            if (indicationOfOngoingQuestion < quizQuestions.size - 1) {
                indicationOfOngoingQuestion++
            } else {
                //Else if the current ongoing question is final , then the quiz game end by putting it to true and the scores will be printed in a toast.
                quizEnd = true
                Toast.makeText(context, "Your score: $totalTally out of ${quizQuestions.size}", Toast.LENGTH_SHORT).show()

            }
            //This resets the button color to darkgrey for the preparation of the next question.
            colourOfTheButton = listOf(Color.DarkGray, Color.DarkGray, Color.DarkGray,Color.DarkGray)
        }


    },
    restartingQuizGame = {
        //the current state of the question goes back to 0 which is the 1st question and the score will be 0 as well and it wil assume that the quiz hasnt end
        indicationOfOngoingQuestion = 0
        totalTally = 0
        quizEnd = false
    }

)
}

@Composable
fun QuizUI(
    //variables are introduced
    quizQuestions: List<String>,//This means that this is a list of quiz questions
    mC: List<List<String>>,//This means that this is a list of multiple choice of each quiz questions
    rightAns: List<Int>,//This means that this is a list of right answer
    selectedAns:(Int, Int) -> Unit,//This represents that the Answer has been selected.
    restartingQuizGame:() -> Unit,//This represents the restart of the quiz game
    indicationOfOngoingQuestion: Int,//This represents that the question is onging.
    totalTally: Int,//Represents the onging score
    quizEnd: Boolean,//Represents the end of quiz
    colourOfTheButton: List<Color>//This represents the button colours which is in a list(red,green,darkgray).
) {
    Column(
        modifier = Modifier
            .padding(17.dp)
    ) {
        if (quizEnd) {
            //if the quiz has ended, the displays a thank you text and the score and the restart button to restart the game
            Column(
                modifier = Modifier.padding(top = 290.dp)
            ) {
                Text(text = "Thank You for playing the game!")
                Text(
                    text = "You scored $totalTally/${quizQuestions.size}",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp),
                    textAlign = TextAlign.Justify

                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = restartingQuizGame,
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFD0B98C)
                    )
                ) {
                    Text("Try Again")
                }

            }
        } else {
            Text(
                //This represents the current ongoing question
                text = quizQuestions[indicationOfOngoingQuestion],
                fontSize = 20.sp,

                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp, top = 180.dp)
            )
            //This represent the current multiple options being show in the current question
            mC[indicationOfOngoingQuestion].forEachIndexed { indication, choice ->
                Button(
                    //and it represents the answer being selected in the current question
                    onClick = { selectedAns(indicationOfOngoingQuestion, indication) },
                    modifier = Modifier
                        .fillMaxWidth()
                        //Space between each buttons
                        .padding(vertical = 8.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = colourOfTheButton[indication]
                    )
                ) {
                    //This is the text that is actually shown in the button
                    Text(choice)
                }
            }

        }
    }



}



