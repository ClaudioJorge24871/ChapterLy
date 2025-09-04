package com.example.chapterly.presentation.ui.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
import javax.inject.Inject

@HiltViewModel
class SaveBookViewModel @Inject constructor(
    private val saveUserBookUseCase: SaveUserBookUseCase
) : ViewModel() {

    private val _saveResult = MutableStateFlow<Result<Unit, Error>>(Result.Loading)
    val saveResult: StateFlow<Result<Unit, Error>> = _saveResult

    /**
     * Saves a book on User library.
     * Loads the books on Success
     */
    fun saveBook(uiData: BookUIDataDTO){
        viewModelScope.launch{
            val entry = uiData.toDomain()
            _saveResult.value = when(val result = saveUserBookUseCase(entry)) {
                is Result.Success -> Result.Success(Unit)
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> {Result.Loading}
            }
        }
    }
}