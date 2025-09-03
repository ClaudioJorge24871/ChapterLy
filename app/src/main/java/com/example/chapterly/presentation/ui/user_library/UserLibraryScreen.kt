package com.example.chapterly.presentation.ui.user_library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.presentation.mapper.toUIData
import com.example.chapterly.resources.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLibraryScreen(viewModel: UserLibraryVIewModel) {

    // Collect the books from StateFlow as Compose state
    val booksResult by viewModel.books.collectAsState()

    // Scaffold for Material3 layout
    Scaffold(
        topBar = { TopAppBar(title = { Text("Your Library") }) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (val result = booksResult) {
                    null -> {
                        Text("Loading books...")
                    }
                    is Result.Success -> {
                        BookList(
                            books = result.data,
                            onDelete = { bookEntry ->
                                viewModel.deleteBook(bookEntry.toUIData())
                            }
                        )
                    }
                    is Result.Error -> {
                        Text("Error loading books: ${result.error}")
                    }
                }
            }
        }
    )
}

@Composable
fun BookList(
    books: List<BookEntry>,
    onDelete: (BookEntry) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { bookEntry ->
            Card(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(bookEntry.book.title, style = MaterialTheme.typography.titleMedium)
                        Text("by ${bookEntry.book.author}", style = MaterialTheme.typography.titleMedium)
                        Text("Status: ${bookEntry.status}", style = MaterialTheme.typography.bodySmall)
                    }
                    Button(onClick = { onDelete(bookEntry) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
