package com.example.tweetsy.screens

import com.example.tweetsy.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

class LoggedInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoggedIn()
        }
    }
}


@Composable
fun LoggedIn() {
    val context = LocalContext.current
    val image = painterResource(id = R.drawable.loading_foreground)

    // Delay before moving to the next activity
    Handler(Looper.getMainLooper()).postDelayed({
        val intent = Intent(context, UserActivity::class.java)
        context.startActivity(intent)

        if (context is LoggedInActivity) {
            context.finish()
        }
    }, 2000)

    // Main layout with black background
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Success Icon
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                color = Color.Green.copy(alpha = 0.2f)
            ) {
                Image(
                    painter = image,
                    contentDescription = "Success",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Success Message
            Text(
                text = "Logged In Successfully",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Loading Indicator
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = Color.Green,
                strokeWidth = 4.dp
            )
        }
    }
}
