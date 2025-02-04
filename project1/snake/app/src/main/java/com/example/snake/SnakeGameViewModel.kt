package com.example.snake

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SnakeGameViewModel:ViewModel() {

    private val _state = MutableStateFlow(SnakeGameState())
    val state = _state.asStateFlow()


    fun onEvent(event: SnakeGameEvent){
         when(event){
             SnakeGameEvent.StartGame  ->{

             }
                     SnakeGameEvent.PauseGame ->{
                         _state.update { it.copy(gameState = GameState.PAUSED) }
                     }
                     SnakeGameEvent.ResetGame ->{
                         _state.value = SnakeGameState()
                     }
                     is SnakeGameEvent.UpdateDirection -> TODO()
         }
    }
}