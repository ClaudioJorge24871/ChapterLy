package com.example.chapterly.presentation.ui.common.helper

import androidx.compose.material3.SnackbarHostState
import com.example.chapterly.presentation.dto.BookUIDataDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Scope

data class BookValidationResult (
    val isValid: Boolean,
    val titleError: Boolean = false,
    val authorError: Boolean = false
)

fun snackBarSaveBookValidation(
    book: BookUIDataDTO,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
): BookValidationResult {
    val errors = mutableListOf<String>()
    var titleError = false
    var authorError = false

    if (book.title.isBlank()){
        errors.add("Title")
        titleError = true
    }
    if(book.author.isBlank()){
        errors.add("Author")
        authorError = true
    }

    return if (errors.isNotEmpty()){
        val message =  "Required: " + errors.joinToString(", ")
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
        BookValidationResult(false,titleError,authorError)
    }else{
        BookValidationResult(true)
    }
}