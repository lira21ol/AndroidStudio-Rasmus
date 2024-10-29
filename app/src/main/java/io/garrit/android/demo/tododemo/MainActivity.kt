package io.garrit.android.demo.tododemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.garrit.android.demo.tododemo.ui.theme.TodoDemoTheme
import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val text: int,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notelist = remember {
                mutableStateListOf<Note>()
            }

            TodoDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(Notes = Noteslist)
                }
            }
        }
    }
}

@Composable
fun MainScreen(list: MutableList<Note<Any?>>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        NoteInputView(notes = notes)
        NoteListView(notes = notes)
    }
}

@Composable
fun NoteInputView(notes: MutableList<Note<Any?>>) {
    var title by rememberSaveable { mutableStateOf("") }
    var text by rememberSaveable { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("")}

        Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(value = title, onValueChange = {
            title = it
        })
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Text") }
            )
            Button(onClick = {
                if (validateTitle(title) && validateText(text)) {
                    notes.add(Note(title = title, text = text))
                    title = ""
                    text = ""
                    errorMessage = ""
                } else {
                    errorMessage = "Title must be 3-50 characters and text must be 120 characters max."
                }
            }) {
                Text("Add")
            }
        }
    if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun NoteListView(notes: List<Note<Any?>>) {
    LazyColumn {
        items(notes) { note ->
            RowView(note = note, onDelete = { notes.remove(note) })
        }
    }
}

@Composable
fun RowView(note: Note<Any?>, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(note.title, modifier = Modifier.weight(1f))
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}
fun validateTitle(title: String): Boolean {
    return title.length in 3..50
}

fun validateText(text: String): Boolean {
    return text.length <= 120
}

@Preview(showBackground = true)
@Composable
fun RowViewPreview() {
    TodoDemoTheme {
        RowView(note = Note(title = "Note", text = "This is a note"))
    }
}