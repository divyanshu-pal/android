package com.example.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.activity.ui.theme.ActivityTheme


class Screen2Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityTheme {
                Screen2(baseActivity = this)
            }
        }
    }


}



@Composable
fun Screen2(baseActivity: BaseActivity) {
    Text(
        text = "Hello Screen2",

        )
    Button( onClick = {baseActivity.goNext(ActivityEnum.SCREEN3)}) {
        Text( text = "Next")
    }

}