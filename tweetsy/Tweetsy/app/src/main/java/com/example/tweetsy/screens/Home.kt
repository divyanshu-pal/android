package com.example.tweetsy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.tweetsy.R
import kotlinx.coroutines.delay

@Composable
fun Home(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(10000) // Wait for 10 seconds before triggering navigation
        onTimeout()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.some_foreground),
                contentDescription = "Success",
                modifier = Modifier
                    .size(68.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = "IRIS",
                color = Color.Blue,
                fontSize = 40.sp,
                letterSpacing = 0.2.em,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
