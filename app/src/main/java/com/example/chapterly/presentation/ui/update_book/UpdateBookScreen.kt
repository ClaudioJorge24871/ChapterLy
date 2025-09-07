package com.example.chapterly.presentation.ui.update_book

import androidx.compose.runtime.Composable
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.ui.add_book.SaveBookScreen
import com.example.chapterly.presentation.ui.add_book.SaveBookViewModel

@Composable
fun UpdateBookScreen(
    viewModel: SaveBookViewModel,
    selectedBook: BookUIDataDTO,
    onBookUpdated: () -> Unit,
    onGoBackClicked: () -> Unit
) {
    SaveBookScreen(
        viewModel = viewModel,
        initialBook = selectedBook,
        onBookSaved = onBookUpdated,
        onGoBackClicked = onGoBackClicked,
        isEdit = true
    )
}