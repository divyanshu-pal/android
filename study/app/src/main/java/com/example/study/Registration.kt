package com.example.study

import android.content.Intent
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
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
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.study.data.model.Register
import com.example.study.ui.theme.StudyTheme
import io.realm.Realm
import androidx.compose.ui.text.input.TextFieldValue
import javax.crypto.KeyGenerator
import javax.crypto.Mac
import kotlin.coroutines.ContinuationInterceptor



import javax.crypto.SecretKey

import java.security.Key

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTheme {
                Registration { navigateToLogin() }
            }
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

//@Preview(showBackground = true)
@Composable
 fun Registration (onRegister: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Registration",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 24.dp)

        )



        OutlinedTextField(
            value = name,
            onValueChange = { newValue ->
                // Prevent leading spaces only during the first character input
                name = if (newValue.isNotBlank() && newValue.startsWith(" ") && name.isEmpty()) {
                    newValue.trimStart()
                } else {
                    newValue
                }
            },

            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),

            )


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val icon = if (showPassword) "Hide" else "Show"
                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(text = icon)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        Button(
            onClick = {
                errorMessage = when {
                    name.isBlank() -> "Name is required."
                    email.isBlank() -> "Email is required."
                    !emailRegex.matches(email) -> "Invalid email format."
                    password.isBlank() -> "Password is required."
                    confirmPassword.isBlank() -> "Please confirm your password."
                    password != confirmPassword -> "Passwords do not match. Please check them."
                    else -> ""
                }

                if (errorMessage.isEmpty()) {
                    if(registerUser(name,email,password)){
                        errorMessage ="Registration Successful!"
                        onRegister()
                    }else{
                        errorMessage = "User already exists!"
                    }

                }
            },
            modifier = Modifier.fillMaxWidth()


        ) {
            Text(text = "Register")
        }
        if(errorMessage.isNotEmpty()){
            Text(text = errorMessage, modifier = Modifier.padding(top = 8.dp))

        }
    }
}

fun remveSpaces(it: String): String {
    return it.replace(" ", "")
}



fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return emailRegex.matches(email)
}


fun getKeyForHashing(): SecretKey {

    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA256, "AndroidKeyStore")

    keyGenerator.apply {
        init(
            KeyGenParameterSpec.Builder("DIVYANSHU12345", KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
    }

    // Generate and return the key as a SecretKey
    return keyGenerator.generateKey()  // Correct return type is SecretKey, not ContinuationInterceptor.Key
}

fun hashPassword(password: String, key: Key): ByteArray {
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(key)
    return mac.doFinal(password.toByteArray(Charsets.UTF_8))
}

fun bytesToHex(bytes: ByteArray): String {
    return bytes.joinToString("") { "%02x".format(it) }
}








fun registerUser(name: String, email: String, password: String): Boolean {

    if (!isValidEmail(email)) {
        return false
    }

    val realm = Realm.getDefaultInstance()
    try {

        val existingUser = realm.where(Register::class.java).equalTo("email", email).findFirst()
        if (existingUser != null) {
            return false
        }

        val hashedPassword = hashPassword(password, getKeyForHashing())
        val hashedPasswordHex = bytesToHex(hashedPassword)

        realm.executeTransaction {
            val newUser = it.createObject(Register::class.java)
            newUser.name = name
            newUser.email = email
            newUser.password = hashedPasswordHex
        }
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    } finally {
        realm.close()
    }
}


