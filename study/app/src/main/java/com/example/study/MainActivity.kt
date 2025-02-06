package com.example.study

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.study.data.model.Person
import com.example.study.ui.theme.StudyTheme
import io.realm.Realm

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            StudyTheme {
//                MyVerticalLayout()
//            }
//        }
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}





class DatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTheme {
                MyVerticalLayout() // Your existing database operations screen
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun MyVerticalLayout() {

    var nameInput by remember { mutableStateOf("") }
    var searchValue by remember { mutableStateOf("") }
    var deleteValue by remember { mutableStateOf("") }
    var allPerson by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Insert
        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            placeholder = { Text("Input name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                addPersonToDatabase(nameInput)
                nameInput = ""
                allPerson = showAllPersons().joinToString()
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = "Add to database")
        }

        Divider()

        // Print all
        Button(
            onClick = { allPerson = showAllPersons().joinToString() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Show all person")
        }

        Text(
            text = allPerson,
            textAlign = TextAlign.Center
        )

        Divider()

        // Search person
        OutlinedTextField(
            value = searchValue,
            onValueChange = { searchValue = it },
            placeholder = { Text("Search with name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { searchResult = searchPerson(searchValue) },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = "Search")
        }

        Text(text = searchResult)

        Divider()

        // Delete person
        OutlinedTextField(
            value = deleteValue,
            onValueChange = { deleteValue = it },
            placeholder = { Text("Delete person with name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (deletePerson(deleteValue)) {
                    deleteValue = ""
                    allPerson = showAllPersons().joinToString()
                }
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = "Delete")
        }
    }
}

fun addPersonToDatabase(name: String) {
    if (name.isEmpty()) return
    Realm
        .getDefaultInstance()
        .executeTransaction { realm ->
            val objectPerson = realm.createObject(Person::class.java)
            objectPerson.name = name
        }
}

fun showAllPersons(): List<String> {
    val data = Realm
        .getDefaultInstance()
        .where(Person::class.java)
        .findAll()
    return data.map { it.name }
}

fun searchPerson(name: String): String {
    if (name.isEmpty()) return "No person found!"
    // add code here
    if (name.isEmpty()) return "Realm: No person found!"
    val person = Realm
        .getDefaultInstance()
        .where(Person::class.java)
        .contains("name", name)
        .findAll()

    return if (person != null && person.isValid) {
        "${person.size} Person found: ${person.joinToString { name }}"
    } else {
        "No person found!"
    }

}

fun deletePerson(name: String): Boolean {
    if (name.isEmpty()) return false
    var result = false
    Realm
        .getDefaultInstance()
        .executeTransaction { transactionRealm ->
            val person = transactionRealm
                .where(Person::class.java)
                .equalTo("name", name)
                .findAll()
            result = person?.let {
                it.deleteAllFromRealm()
                println("Realm: Item deleted: $name")
                true
            } ?: run {
                println("Realm: Unable to delete the item")
                false
            }
        }
    return result
}