package com.example.chapterly.presentation.ui.user_library

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.chapterly.domain.model.BookEntry

@Composable
fun UserLibraryRoute(
    onAddBookClick: () -> Unit,
    viewModel: UserLibraryViewModel = hiltViewModel(),
    onSelectedBook: (Int) -> Unit,
    onChangeStatus: (BookEntry) -> Unit
){
    UserLibraryScreen(
        viewModel = viewModel,
        onAddBookClick = onAddBookClick,
        onSelectedBook = onSelectedBook,
        onChangeStatus = onChangeStatus
    )
}