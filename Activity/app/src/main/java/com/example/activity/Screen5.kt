package com.example.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.activity.ui.theme.ActivityTheme


class Screen5Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityTheme {
                Screen5(baseActivity = this)
            }
        }
    }


}



@Composable
fun Screen5(baseActivity: BaseActivity) {
    Text(
        text = "Hello Screen5",

        )



}