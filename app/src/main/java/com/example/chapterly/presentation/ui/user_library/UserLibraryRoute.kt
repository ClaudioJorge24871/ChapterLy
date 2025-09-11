package com.example.chapterly.presentation.ui.user_library

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
fun UserLibraryRoute(
    onAddBookClick: () -> Unit,
    viewModel: UserLibraryViewModel = hiltViewModel(),
    onSelectedBook: (Int) -> Unit
){
    UserLibraryScreen(
        viewModel = viewModel,
        onAddBookClick = onAddBookClick,
        onSelectedBook = onSelectedBook
    )
}