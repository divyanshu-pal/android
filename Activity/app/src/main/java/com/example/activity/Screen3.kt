package com.example.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.activity.ui.theme.ActivityTheme


class Screen3Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityTheme {
                Screen3(baseActivity = this)
            }
        }
    }


}


@Composable
fun Screen3(baseActivity: BaseActivity) {
    Text(
        text = "Hello Screen3",

        )

    Button( onClick = {baseActivity.goNext(ActivityEnum.SCREEN4)}) {
        Text( text = "Next")
    }

}