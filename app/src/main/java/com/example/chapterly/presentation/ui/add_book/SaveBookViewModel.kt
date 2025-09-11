package com.example.chapterly.presentation.ui.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapterly.data.local.dao.BookDao
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import com.example.chapterly.domain.use_case.UpdateUserBookUseCase
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
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

    /**
     * Saves a book on User library.
     * Loads the books on Success
     */
    fun saveBook(uiData: BookUIDataDTO){
        viewModelScope.launch{
            val entry = uiData.toDomain()
            when(saveUserBookUseCase(entry)) {
                is Result.Success -> _saveEvent.send(Unit) // emit event once
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }


    fun updateBook(uiData: BookUIDataDTO){
        viewModelScope.launch {
            val entry = uiData.toDomain()
            when (updateUserBookUseCase(entry)) {
                is Result.Success -> _saveEvent.send(Unit)
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

    }
}