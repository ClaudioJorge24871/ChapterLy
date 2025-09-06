package com.example.chapterly.presentation.ui.user_library.components

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.chapterly.domain.model.BookEntry

@Composable
fun BookList(
    books: List<BookEntry>,
    onDelete: (BookEntry) -> Unit,
    onSelectedBook: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Two books per row
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { bookEntry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                onClick = {onSelectedBook(bookEntry.book.isbn)}
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column (
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = bookEntry.book.title, style = MaterialTheme.typography.titleLarge,
                            maxLines = 2, overflow = TextOverflow.Ellipsis
                        )
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Row(
                            ) {
                                Text(text= "by ", style = MaterialTheme.typography.titleSmall, color = Color.Gray)
                                Text(text = bookEntry.book.author.toString(), style = MaterialTheme.typography.titleMedium)
                            }
                            Text("Status: ${bookEntry.status.displayName}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    IconButton(
                        onClick = { onDelete(bookEntry) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete book"
                        )
                    }
                }
            }
        }
    }

}