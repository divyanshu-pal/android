package com.example.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.activity.ui.theme.ActivityTheme

class MainScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityTheme {
                MainScreen()
            }
        }
    }



    @Composable
    fun MainScreen() {
        Scaffold { paddingValues ->
            // Use a Column to arrange the Text and Button vertically
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp), // Add padding to the content
                verticalArrangement = Arrangement.Center, // Center content vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
            ) {
                Text(
                    text = "Welcome to the Main Screen!",
                    modifier = Modifier.padding(bottom = 16.dp) // Add spacing below the Text
                )
                Button(
                    onClick = { goNext(ActivityEnum.SCREEN1) } // Directly call the function
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}


