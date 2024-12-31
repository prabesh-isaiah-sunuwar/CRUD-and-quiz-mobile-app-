package com.example.as1

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginActivity(loginSuccessful: ()-> Unit){
    //"blank by remember mutableStateOf" means that it is capable of changing in the id and password field.
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //Used for Toast
    val context = LocalContext.current.applicationContext

    Box(
        //Box is used to color the whole page
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0E6)),
        contentAlignment = Alignment.Center,


        ) {
        Column(
//This Column is used to control the whole space by putting the whole content to the center and pad the entire content of the header title to the button
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()


        ) {
            //Image is the school logo
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your logo drawable resource
                contentDescription = "Logo",
                modifier = Modifier.fillMaxWidth() // Adjust size as needed
            )
            //This Spacer leaves a space between the two layouts which is the "School Logo" and Header Title.
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Please enter your ID", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(1.dp))
//TextBox for LoginId field
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("Login ID") },
                colors = TextFieldDefaults.colors(
                    //When the text field is click for focusedLeadingIconColor
                    focusedLeadingIconColor = Color.LightGray,
                    //When the text field is not clicked for unfocusedLeadingIconColor
                    unfocusedLeadingIconColor = Color.DarkGray,
                    focusedLabelColor =  Color(0xFFD0B98C),
                    unfocusedLabelColor = Color(0xFFF5F0E6),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color(0xFFD0B98C)

                ), leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Username")
                },
                modifier = Modifier.fillMaxWidth()
            )
//This Spacer leaves a space between the two layouts which is the Id field and password field.
            Spacer(modifier = Modifier.height(8.dp))
//TextBox for LoginId field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),//this line is it makes the plain text password in dots
                colors = TextFieldDefaults.colors(
                    //When the text field is click for focusedLeadingIconColor
                    focusedLeadingIconColor = Color.LightGray,
                    //When the text field is not clicked for unfocusedLeadingIconColor
                    unfocusedLeadingIconColor = Color.DarkGray,
                    focusedLabelColor =  Color(0xFFD0B98C),
                    unfocusedLabelColor = Color(0xFFF5F0E6),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color(0xFFD0B98C),

                ),leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                },
                modifier = Modifier.fillMaxWidth()
            )

//This Spacer leaves a space between the two layouts which is the password field and button.
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (authenticate(id, password)){
                        loginSuccessful()
                        Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()


                    } else{
                        Toast.makeText(context, "Invalid Id or Password", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.size(100.dp , 50.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFD0B98C),
                )


            ) {
                Text(text = "Login")
            }
        }
    }
}



private fun authenticate(id: String,password: String): Boolean{
    val verifyId = ""
    val verifyPassword = ""
    return id == verifyId && password == verifyPassword
}

