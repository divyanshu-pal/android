package com.example.task2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Layout() {
 val configuration = LocalConfiguration.current
 val isLandscape = configuration.orientation == 2
 val name = remember { mutableStateListOf("Sterility","BET","Identifiation(by HPCL)","Uniformity of Dosage Units","Identifiation(by HPCL)","Uniformity of Dosage Units") }


 Scaffold(
  topBar = {
   TopAppBar(
    colors = topAppBarColors(
     containerColor = Color.Black

     ,
    ),
    title = {
     if (isLandscape) {
      // 🔹 Horizontal Layout for Landscape Mode
      Row(
       modifier = Modifier.fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween
      ) {
       Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
         containerColor = Color.Black,
         contentColor = Color.Blue
        )
       ) {
        Text("Back", modifier = Modifier.size(width = 60.dp, height = 40.dp))
       }

       Text("Get Samples For", color = Color.White)

       Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
         containerColor = Color.Gray,
         contentColor = Color.Blue
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(2.dp)
         .padding(end=30.dp),
       ) {
        Text("Menu")
       }
      }
     } else {
      // 🔹 Vertical Layout for Portrait Mode
      Column(
       modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp), // Ensure space for all elements
       horizontalAlignment = Alignment.CenterHorizontally
      ) {
//       Button(
//        onClick = {},
//        colors = ButtonDefaults.buttonColors(
//         containerColor = Color.Black,
//         contentColor = Color.Blue
//        ),
//        modifier = Modifier.padding(bottom = 8.dp) // Space between elements
//       ) {
//        Text("Back")
//       }

       Text("Get Samples For", color = Color.White)

//       Button(
//        onClick = {},
//        colors = ButtonDefaults.buttonColors(
//         containerColor = Color.Gray,
//         contentColor = Color.Blue
//        ),
//        shape = RoundedCornerShape(4.dp),
//        modifier = Modifier.padding(top = 8.dp) // Space between elements
//       ) {
//        Text("Menu")
//       }
      }
     }
    }
   )


  },



 )
 { innerPadding ->

  Column(
   modifier = Modifier
    .padding(innerPadding)
    .padding(top = 10.dp)
    .padding(end=50.dp)
    .padding(bottom = 40.dp),


   verticalArrangement = Arrangement.spacedBy(16.dp),
   horizontalAlignment = Alignment.CenterHorizontally,

  ) {

   if(isLandscape) {

    LazyVerticalGrid(
     columns = GridCells.Fixed(2),
     modifier = Modifier.fillMaxWidth(),
     contentPadding = PaddingValues(16.dp),
     horizontalArrangement = Arrangement.spacedBy(16.dp),
     verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
     items(6) { index ->
      CardLayout(index + 1,name[index])
     }
    }

   }else{
     for(i in 1..6){
       CardLayout(i,name[i-1])
     }
   }


  }
 }
}

