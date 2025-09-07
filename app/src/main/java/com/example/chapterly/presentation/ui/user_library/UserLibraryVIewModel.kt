package com.example.chapterly.presentation.ui.user_library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.use_case.DeleteUserBookUseCase
import com.example.chapterly.domain.use_case.GetUserBookByISBNUseCase
import com.example.chapterly.domain.use_case.GetUserBooksUseCase
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.UnknownError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserLibraryViewModel @Inject constructor (
    private val deleteUserBookUseCase: DeleteUserBookUseCase,
    private val getUserBooksUseCase: GetUserBooksUseCase,
    private val getUserBookByISBNUseCase: GetUserBookByISBNUseCase
): ViewModel(){

    // StateFlow: Only the ViewModel can change it
    private val _books = MutableStateFlow<Result<List<BookEntry>, Error>>(Result.Loading)
    val books: StateFlow<Result<List<BookEntry>, Error>> = _books

    //Selected single book state (used for editing)
    private val _selectedBook = MutableStateFlow<Result<BookEntry, Error>?>(null)
    val selectedBook: StateFlow<Result<BookEntry, Error>?> = _selectedBook

    init {
        loadBooks()
    }

    /**
     * Launches a coroutine on the View Model scope
     */
    fun loadBooks() {
        viewModelScope.launch {
            getUserBooksUseCase().collect { result ->
                _books.value = result
            }
        }
    }

    /**
     * Fetch a single book by ISBN and expose it through selectedBook StateFlow.
     * Uses Dispatchers.IO because the repository/DAO call may block.
     */
    fun getUserBookByISBN(isbn: String) {
        viewModelScope.launch {
            _selectedBook.value = Result.Loading
            val result = withContext(Dispatchers.IO) {
                try {
                    getUserBookByISBNUseCase(isbn)
                } catch (e: Exception) {
                    Result.Error(UnknownError(e.message ?: "Unknown"))
                }
            }
            _selectedBook.value = result
        }
    }

    fun clearSelectedBook() {
        _selectedBook.value = null
    }

    /**
     * Deletes a book from the User Library.
     * Loads the books on success
     */
    fun deleteBook(uiData: BookUIDataDTO){
        viewModelScope.launch {
            val entry = uiData.toDomain()
            when(val result = deleteUserBookUseCase(entry)) {
                is Result.Success -> loadBooks()
                is Result.Error -> {
                    //TODO update UI error state or log
                }
                is Result.Loading -> {
                    //Ignore
                }
            }
        }
    }

}