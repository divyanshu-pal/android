package com.example.tweetsy.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Register()
        }
    }
}


@Composable
fun Register() {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Name field with stable colors
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = {
                    Text(
                        text = "Name",
                        color = Color.White
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Gray,
//                    unfocusedBorderColor = Color.Gray,
//                    cursorColor = Color.White,
//                    focusedTextColor = Color.White,
//                    unfocusedTextColor = Color.White
//                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email field with stable colors
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = {
                    Text(
                        text = "Email",
                        color = Color.White
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Gray,
//                    unfocusedBorderColor = Color.Gray,
//                    cursorColor = Color.White,
//                    focusedTextColor = Color.White,
//                    unfocusedTextColor = Color.White
//                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password field with stable colors
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = {
                    Text(
                        text = "Password",
                        color = Color.White
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Gray,
//                    unfocusedBorderColor = Color.Gray,
//                    focusedTextColor = Color.White,
//                    unfocusedTextColor = Color.White,
//                    cursorColor = Color.White
//                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val intent = Intent(context, LoggedInActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Proceed",
                    color = Color.White
                )
            }
        }
    }
}
