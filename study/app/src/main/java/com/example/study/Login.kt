package com.example.study

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.study.data.model.Register
import com.example.study.ui.theme.StudyTheme
import io.realm.Realm


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTheme {
                Login { navigateToDatabase() }
            }
        }
    }

    private fun navigateToDatabase() {
        val intent = Intent(this, DatabaseActivity::class.java)
        startActivity(intent)
        finish() // Close the LoginActivity
    }
}




//@Preview(showBackground = true)
@Composable
 fun Login (onLogin: () -> Unit) {

     var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

   var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                   .padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),


        )

        Spacer(modifier = Modifier.height(16.dp))

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        Button(
             onClick = {
                 errorMessage = when{
                     email.isBlank()-> "Email is required"
                     !emailRegex.matches(email) -> "Invalid email format."
                     password.isBlank()->"Password is requried"
                     else -> ""
                 }
                 if(errorMessage.isEmpty()){
                    if(loginUser(email,password)){
                        onLogin()
                    }else{
                        errorMessage = "User not exist.Please Register"
                    }
                 }

             },
            modifier = Modifier.fillMaxWidth()


        ) {
            Text("Login")
        }

    }

    
}

fun ValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return emailRegex.matches(email)
}

fun loginUser(email: String, password: String): Boolean {

    if (!ValidEmail(email)) {
        return false // Reject invalid email format
    }

    val realm = Realm.getDefaultInstance()
    try {
        // Check if the user exists in the database and the password matches
        val user = realm.where(Register::class.java)
            .equalTo("email", email)
            .equalTo("password", password)
            .findFirst()

        return user != null // Return true if user is found and credentials match, otherwise false

    } catch (e: Exception) {
        e.printStackTrace() // Log error if any exception occurs
        return false
    } finally {
        realm.close() // Always close the Realm instance to avoid memory leaks
    }
}
