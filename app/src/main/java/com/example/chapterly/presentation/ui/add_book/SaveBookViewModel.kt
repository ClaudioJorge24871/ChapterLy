package com.example.chapterly.presentation.ui.add_book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chapterly.data.local.dao.BookDao
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Status
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import com.example.chapterly.domain.use_case.UpdateUserBookUseCase
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.presentation.mapper.toUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SaveBookViewModel @Inject constructor(
    private val saveUserBookUseCase: SaveUserBookUseCase,
    private val updateUserBookUseCase: UpdateUserBookUseCase
) : ViewModel() {

    private val _saveEvent = Channel<Unit>(Channel.BUFFERED)
    val saveEvent = _saveEvent.receiveAsFlow()

    var book by mutableStateOf(BookUIDataDTO())
        private set

    fun initBook (initialBook: BookUIDataDTO){
        book = initialBook
    }

    fun updateField(update: (BookUIDataDTO) -> BookUIDataDTO) {
        book = update(book)
    }

    fun updateStatusSequentially(bookEntry: BookEntry){
        val newStatus = when(bookEntry.status){
            Status.TO_READ -> Status.READING
            Status.READING -> Status.FINISHED
            Status.FINISHED -> Status.TO_READ
        }

        val currentPage = if(newStatus == Status.FINISHED){
            bookEntry.book.pagination
        }else bookEntry.currentPage

        val uiData = bookEntry.copy(status = newStatus, currentPage = currentPage)
            .toUIData()

        viewModelScope.launch {
            val entry = uiData.toDomain()
            when (updateUserBookUseCase(entry)) {
                is Result.Success -> {
                    clearBook()
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    fun clearBook(){
        book = BookUIDataDTO()
    }

    /**
     * Saves a book on User library.
     * Loads the books on Success
     */
    fun saveBook(uiData: BookUIDataDTO){
        viewModelScope.launch{
            val entry = uiData.toDomain()
            when(saveUserBookUseCase(entry)) {
                is Result.Success -> {
                    clearBook()
                    _saveEvent.send(Unit)
                } // emit event once
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }


    fun updateBook(uiData: BookUIDataDTO){
        viewModelScope.launch {
            val entry = uiData.toDomain()
            when (updateUserBookUseCase(entry)) {
                is Result.Success -> {
                    clearBook()
                    _saveEvent.send(Unit)
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }
}