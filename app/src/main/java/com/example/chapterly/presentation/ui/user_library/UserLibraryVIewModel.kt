package com.example.chapterly.presentation.ui.user_library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.use_case.DeleteUserBookUseCase
import com.example.chapterly.domain.use_case.GetUserBooksUseCase
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLibraryVIewModel @Inject constructor (
    private val deleteUserBookUseCase: DeleteUserBookUseCase,
    private val getUserBooksUseCase: GetUserBooksUseCase,
    private val saveUserBookUseCase: SaveUserBookUseCase
): ViewModel(){

    // StateFlow: Only the ViewModel can change it
    private val _books = MutableStateFlow<Result<List<BookEntry>, Error>?>(null)
    val books: StateFlow<Result<List<BookEntry>, Error>?> = _books

    /**
     * Launches a coroutine on the View Model scope
     */
    fun loadBooks() {
        viewModelScope.launch {
            val result = getUserBooksUseCase()
            _books.value = result
        }
    }

    /**
     * Saves a book on User library.
     * Loads the books on Success
     */
    fun saveBook(uiData: BookUIDataDTO){
        viewModelScope.launch{
            val entry = uiData.toDomain()
            when(val result = saveUserBookUseCase(entry)) {
                is Result.Success -> loadBooks()
                is Result.Error -> {
                    //TODO update UI error state or log
                }
            }
        }
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
            }
        }
    }

}