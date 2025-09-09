package com.example.chapterly.presentation.ui.add_book

import android.R
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.presentation.mapper.toLocalDateOrNull
import com.example.chapterly.presentation.ui.common.DateField
import com.example.chapterly.presentation.ui.common.helper.snackBarSaveBookValidation
import com.example.chapterly.resources.Result
import kotlinx.coroutines.selects.select
import org.intellij.lang.annotations.JdkConstants
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveBookScreen(
    viewModel: SaveBookViewModel,
    onBookSaved: () -> Unit,
    onGoBackClicked: () -> Unit,
    onDeleteBook: (BookEntry) -> Unit,
    initialBook: BookUIDataDTO = BookUIDataDTO(),
    isEdit: Boolean = false
) {
    val context = LocalContext.current

    var book by remember { mutableStateOf(initialBook) }

    val saveEvent by viewModel.saveEvent.collectAsState(initial = null)

    // Variables for data validation
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var yearError by remember { mutableStateOf<String?>(null) }
    var paginationError by remember {mutableStateOf<String?>(null)}
    var titleError by remember { mutableStateOf(false) }
    var authorError by remember { mutableStateOf(false) }

    Scaffold (
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {Text(text = if(isEdit) "Update: '${initialBook.title}'" else "Add a new book")},
                navigationIcon = {
                    IconButton(
                        onClick = {onGoBackClicked()}
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if(isEdit){
                        IconButton(
                            onClick = { onDeleteBook(initialBook.toDomain()) },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete book"
                            )
                        }
                    }
                }
            )
         },
        floatingActionButton = { // save book
            ExtendedFloatingActionButton(
                onClick = {
                    val result = snackBarSaveBookValidation(book, coroutineScope, snackBarHostState)
                    titleError = result.titleError
                    authorError = result.authorError

                    if(result.isValid){
                        if(isEdit){
                            viewModel.updateBook(book)
                        }else{
                            viewModel.saveBook(book)
                        }
                    }
                },
                text = {Text(if(isEdit) "Update" else "Save")},
                icon = {Icon(Icons.Default.Check, contentDescription = null)},
            )
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,
    ){ padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                OutlinedTextField(
                    value = book.title,
                    onValueChange = {book = book.copy(title = it)},
                    label = {Text(text = "Title")},
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = titleError
                )
                OutlinedTextField(
                    value = book.author,
                    onValueChange = {book = book.copy(author = it)},
                    label = {Text(text = "Author")},
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = authorError
                )
            }
            OutlinedTextField(
                value = book.isbn,
                onValueChange = {book = book.copy(isbn = it)},
                label = {Text(text = "ISBN")},
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                OutlinedTextField(
                    value = book.publisher,
                    onValueChange = { book = book.copy(publisher = it) },
                    label = { Text("Publisher") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = book.publishYear,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            if (newValue.length > 4) {
                                yearError = "Year must be under 5 digits"
                                book = book.copy(publishYear = newValue.take(4))
                            } else{
                                yearError = null
                                book = book.copy(publishYear = newValue)
                            }
                        }
                    },
                    label = { Text("Year") },
                    modifier = Modifier.width(120.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = yearError != null,
                    supportingText = {
                        yearError?.let{
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                OutlinedTextField(
                    value = book.edition ?: "",
                    onValueChange = { book = book.copy(edition = it) },
                    label = { Text("Edition") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = book.pagination,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            if(newValue.length > 5){
                                paginationError = "Pagination must be under 6 digits"
                                book = book.copy(pagination = newValue.take(5))
                            }else{
                                paginationError = null
                                book = book.copy(pagination = newValue)
                            }
                        }
                    },
                    label = { Text("Pagination") },
                    modifier = Modifier.width(120.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = paginationError != null,
                    supportingText = {
                        paginationError?.let{
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
            }

            DateField(
                label = "Purchase Date",
                date = book.purchaseDate.toLocalDateOrNull(),
                onDateSelected = { selected ->
                    book = book.copy(purchaseDate = selected.toString())
                }
            )
            DateField(
                label = "Start Date",
                date = book.startDate.toLocalDateOrNull(),
                onDateSelected = { selected ->
                    book = book.copy(startDate = selected.toString())
                }
            )
            DateField(
                label = "End Date",
                date = book.endDate.toLocalDateOrNull(),
                onDateSelected = { selected ->
                    book = book.copy(endDate = selected.toString())
                }
            )

            /**
            OutlinedTextField(
            value = book.coverImageURL,
            onValueChange = {book = book.copy(coverImageURL = it)},
            label = {Text("Image URL")},
            modifier = Modifier.fillMaxWidth()
            )
             */
            Spacer(modifier = Modifier.height(16.dp))

            // Collects the one-time save event from the ViewModel.
            // When a new event is emitted (i.e., the book is successfully saved or updated),
            // the LaunchedEffect runs and calls onBookSaved(), triggering navigation or other actions.
            // This ensures the effect only happens once per save and does not re-run on recompositions.
            LaunchedEffect(saveEvent) {
                saveEvent?.let { onBookSaved() }
            }
        }
    }
}