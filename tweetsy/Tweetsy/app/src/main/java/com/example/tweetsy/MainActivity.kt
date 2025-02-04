package com.example.tweetsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import com.example.tweetsy.screens.Home
import com.example.tweetsy.screens.Login

class MainActivity : ComponentActivity() {
    private lateinit var realm: Realm // Declare Realm instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Realm
        val config = RealmConfiguration.Builder(
            schema = setOf(Register::class) // Include your Realm models
        ).build()
        realm = Realm.open(config)

        enableEdgeToEdge()
        setContent {
            App(realm) // Pass Realm to the composable function
        }
    }
}





@Composable
fun App(realm: Realm) {
    var showHome by remember { mutableStateOf(true) }

    if (showHome) {
        Home() {
            showHome = false
        }
    } else {
        Login(realm)
    }
}
