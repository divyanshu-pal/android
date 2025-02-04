package com.example.snake



import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.snake.ui.theme.Citrine
import com.example.snake.ui.theme.Custard
import com.example.snake.ui.theme.RayalBlue
import androidx.compose.ui.unit.IntSize as IntSize1


@Composable
fun SnakeGameScreen(state: SnakeGameState,
                    onEvent: (SnakeGameEvent)->Unit) {
    val foodImageBitmap = ImageBitmap.imageResource(id = R.drawable.img)


    val snakeHeadImageBitmap = when(state.direction){

         Direction.UP -> ImageBitmap.imageResource(id = R.drawable.img_1)
        Direction.DOWN -> ImageBitmap.imageResource(id = R.drawable.img_2)
        Direction.LEFT -> ImageBitmap.imageResource(id = R.drawable.img_3)
        Direction.RIGHT -> ImageBitmap.imageResource(id = R.drawable.img_4)
    }

    val  context = LocalContext.current
    val foodSoundMp = remember { MediaPlayer.create(context,R.raw.food) }
    val gameOverSoundMp = remember { MediaPlayer.create(context,R.raw.gameover) }

    LaunchedEffect ( key1 = state.snake.size ){
         if(state.snake.size !=1){
             foodSoundMp?.start()
         }
    }

    LaunchedEffect(key1 = state.isGameOver){
         if(state.isGameOver){
             gameOverSoundMp?.start()
         }
    }

    Box(modifier = Modifier.fillMaxSize(),
//          contentAlignment = Alignment.Centent,
    )
     Column(
         modifier = Modifier.fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
     ) {
          Card (
              modifier = Modifier.padding(8.dp)
                  .fillMaxWidth(1f)
          ){
              Text(
                   modifier = Modifier.padding(16.dp),
                  text = "Score:${state.snake.size - 1}",
                  style = MaterialTheme.typography.headlineMedium

              )
          }
        Canvas(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(ratio = 2/3f)
                .pointerInput(state.gameState){
                    if(state.gameState != GameState.STARTED){
                         return@pointerInput
                    }
                    detectTapGestures { offset -> onEvent(SnakeGameEvent.UpdateDirection(offset,size.width))  }
                }
        ) {
            var cellSize = size.width / 20
            drawGameBoard(
                 cellSize = cellSize,
                 cellColor = Custard,
                 borderCellColor = RayalBlue,
                gridWidth = 20,
                gridHeight = 30,

            )
           drawFood(
               foodImage =foodImageBitmap,
               cellSize = cellSize.toInt(),
               coordinate= state.food
           )

            drawSnake(
                snakeHeadImage =snakeHeadImageBitmap ,
                cellSize = cellSize,
                snake = state.snake
            )

        }

         Row(
              modifier = Modifier.padding(8.dp)
                  .fillMaxWidth()
         ){
             Button(
                 modifier = Modifier.weight(1f),
                 onClick = {onEvent(SnakeGameEvent.ResetGame)},
                 enabled = state.gameState == GameState.PAUSED || state.isGameOver
             ) {
                 Text(
                     text = "Replay"
                 )
             }
            Spacer(modifier = Modifier.width(10.dp))
             Button(
                 modifier = Modifier.weight(1f),
                 onClick = {}
             ) {
                 Text(text = if (state.isGameOver) "Reset" else "New Game")
             }
             Spacer(modifier = Modifier.width(10.dp))
             Button(
                 modifier = Modifier.weight(1f),
                 onClick = {
                     when (state.gameState) {
                         GameState.IDLE, GameState.PAUSED -> onEvent(SnakeGameEvent.StartGame)
                         GameState.STARTED -> onEvent(SnakeGameEvent.PauseGame)
                     }
                 },
                 enabled = !state.isGameOver
             ){
                 Text(
                     text = when (state.gameState) {
                         GameState.IDLE -> "Start"
                         GameState.STARTED -> "Pause"
                         GameState.PAUSED -> "Resume"
                     }
                 )
             }

         }

     }
    AnimatedVisibility(visible = state.isGameOver) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Game Over",
            style = MaterialTheme.typography.displayMedium
        )
    }

}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private fun DrawScope.drawGameBoard(
       cellSize:Float,
       cellColor:Color,
       borderCellColor: Color,
       gridWidth: Int,
       gridHeight: Int,
){
          
    for(i in 0 until  gridWidth ){
         for(j in 0 until gridHeight){
             val isBorderCell = i==0 || j==0 || i==gridWidth -1 || j == gridHeight-1
             drawRect(
                 color = if (isBorderCell) borderCellColor else if ((i+j)%2 == 0)cellColor
                  else cellColor.copy(0.5f),

                 topLeft = Offset(x = i*cellSize,y=j*cellSize),
                 size = Size(cellSize,cellSize)
             )
         }

    }
}



private fun DrawScope.drawFood(
     foodImage: ImageBitmap,
     cellSize: Int,
     coordinate: Coordinate
){
    drawImage(
        image = foodImage,
        dstOffset = IntOffset(
            x = (coordinate.x * cellSize),
            y = (coordinate.y * cellSize)
        ),
        dstSize = IntSize(cellSize, cellSize)

    )
}

private fun DrawScope.drawSnake(
    snakeHeadImage: ImageBitmap,
    cellSize: Float,
    snake: List<Coordinate>
) {
    val cellSizeInt = cellSize.toInt()
    snake.forEachIndexed { index, coordinate ->
        val radius = if (index == snake.lastIndex) cellSize / 2.5f else cellSize / 2
        if (index == 0) {
            drawImage(
                image = snakeHeadImage,
                dstOffset = IntOffset(
                    x = (coordinate.x * cellSizeInt),
                    y = (coordinate.y * cellSizeInt)
                ),
                dstSize = IntSize(cellSizeInt, cellSizeInt)
            )
        } else {
            drawCircle(
                color = Citrine,
                center = Offset(
                    x = (coordinate.x * cellSize) + radius,
                    y = (coordinate.y * cellSize) + radius
                ),
                radius = radius
            )
        }
    }
}