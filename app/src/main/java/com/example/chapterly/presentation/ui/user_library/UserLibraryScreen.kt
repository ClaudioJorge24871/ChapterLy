package com.example.chapterly.presentation.ui.user_library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.presentation.mapper.toUIData
import com.example.chapterly.presentation.ui.user_library.components.BookList
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
import org.intellij.lang.annotations.JdkConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLibraryScreen(
    viewModel: UserLibraryViewModel,
    onAddBookClick: () -> Unit,
    onSelectedBook: (String) -> Unit
) {
    // Collect the books from StateFlow as Compose state
    val booksResult by viewModel.books.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Your Library") })},
        floatingActionButton = {
            if (
                    booksResult is Result.Success
                && (booksResult as Result.Success<List<BookEntry>, Error>).data.isNotEmpty()
            ){
                FloatingActionButton(
                    onClick = {onAddBookClick()}
                ) {
                    Icon(Icons.Filled.Add, "Add book")
                }
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(8.dp))

            when (val result = booksResult) {
                is Result.Loading -> {
                    Text("Loading books...")
                }
                is Result.Success -> {
                    if (result.data.isEmpty()){ // If the user doesn't have any books added
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "It looks like you don't have any books saved.",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Button(
                                    onClick = {onAddBookClick()},
                                ) {
                                    Text(text = "Add books to your library")
                                }
                            }
                        }
                    }else{
                        BookList(
                            books = result.data,
                            onSelectedBook = { isbn -> onSelectedBook(isbn) }
                        )
                    }
                }
                is Result.Error -> {
                    Text("Error loading books: ${result.error}")
                }
            }
        }
    }
}
