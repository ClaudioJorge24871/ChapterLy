package com.example.chapterly.presentation.ui.add_book

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SaveBookScreen(
    viewModel: SaveBookViewModel,
    onBookSaved: () -> Unit
) {
    Text(text = "Save Book page")
}