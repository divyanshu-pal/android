package com.example.activity

import android.os.Bundle
import android.view.SearchEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.activity.ui.theme.ActivityTheme

class Screen1Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityTheme {
                Screen1(baseActivity = this)
            }
        }
    }


}

@Composable
fun Screen1(baseActivity: BaseActivity) {
    Text(
        text = "Hello Screen1"
    )

    Button( onClick = {baseActivity.goNext(ActivityEnum.SCREEN2)}) {
         Text( text = "Next")
    }
}

