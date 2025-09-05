package com.example.chapterly.presentation.ui.add_book

import android.graphics.Outline
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.resources.Result
import org.intellij.lang.annotations.JdkConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveBookScreen(
    viewModel: SaveBookViewModel,
    onBookSaved: () -> Unit
) {
    var book by remember { mutableStateOf(BookUIDataDTO()) }

    val saveResult by viewModel.saveResult.collectAsState()

    Scaffold (
        topBar = { TopAppBar(title = {Text(text = "Save a book")}) },
        floatingActionButton = { // save book
            if (book.title.isNotBlank() && book.author.isNotBlank()) {
                FloatingActionButton(
                    onClick = { viewModel.saveBook(book) }
                ) {
                    Text("Save")
                }
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End
    ){ padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            OutlinedTextField(
                value = book.title,
                onValueChange = {book = book.copy(title = it)},
                label = {Text(text = "Title")},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = book.author,
                onValueChange = {book = book.copy(author = it)},
                label = {Text(text = "Author")},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = book.isbn,
                onValueChange = {book = book.copy(isbn = it)},
                label = {Text(text = "ISBN")},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = book.pagination,
                onValueChange = { book = book.copy(pagination = it) },
                label = { Text("Pages") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = book.publisher,
                onValueChange = { book = book.copy(publisher = it) },
                label = { Text("Publisher") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = book.publishYear,
                onValueChange = { book = book.copy(publishYear = it) },
                label = { Text("Publish Year") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            //Show status
            when(saveResult){
                is Result.Loading -> CircularProgressIndicator()
                is Result.Error -> Text(
                    text = "Error saving book: ${(saveResult as Result.Error).error}"
                )
                is Result.Success -> LaunchedEffect(Unit) {
                    onBookSaved()
                }
            }
        }
    }
}