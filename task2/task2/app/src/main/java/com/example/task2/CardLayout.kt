package com.example.task2

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task2.ui.theme.Purple80

@Preview
@Composable
fun CardLayout(number: Int,cardName:String) {

    var isHovered by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.Top
    ) {
        // Canvas to draw the circle and the number
        Canvas(
            modifier = Modifier
                .size(40.dp)
                .padding(start = 4.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {

                            isHovered = !isHovered
                        }
                    )
                }
        ) {
            // Draw the circle with dynamic color change on touch

            val strokeWidth = 1.dp.toPx()
            drawCircle(
                color = Color.Gray,
                radius = (size.minDimension / 2), // Outer circle (border)
                center = center,
            )

            // Draw inner circle with dynamic color
            drawCircle(
                color = if (isHovered) Purple80 else Color.Black,
                radius = (size.minDimension / 2) - strokeWidth, // Inner circle slightly smaller
                center = center,
            )
            drawIntoCanvas { canvas ->
                val textPaint = Paint().apply {
                    color = 0xFFFFFFFF.toInt()  // Set white color
                    textAlign = Paint.Align.CENTER
                    textSize = 52f
                }

                val text = "$number"  // Text to display inside the circle (dynamic number)
                val textX = size.width / 2
                val textY = size.height / 2 - (textPaint.ascent() + textPaint.descent()) / 2

                canvas.nativeCanvas.drawText(text, textX, textY, textPaint)
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            border = BorderStroke(2.dp, if (isHovered) MaterialTheme.colorScheme.primary else Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 8.dp)
        ) {
            Text(
                text =cardName,
                modifier = Modifier
                    .padding(24.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}
