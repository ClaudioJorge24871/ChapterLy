package com.example.chapterly.presentation.view_model

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.use_case.DeleteUserBookUseCase
import com.example.chapterly.domain.use_case.GetUserBooksUseCase
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import com.example.chapterly.presentation.dto.BookUIDataDTO
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error


class UserLibraryVIewModel (
    private val deleteUserBookUserCase: DeleteUserBookUseCase,
    private val getUserBooksUseCase: GetUserBooksUseCase,
    private val saveUserBookUseCase: SaveUserBookUseCase
){
    suspend fun saveBook(uiData: BookUIDataDTO): Result<BookEntry, Error>{
        val entry = uiData.toDomain()
        return saveUserBookUseCase(entry)
    }

    suspend fun getBooks(): Result<List<BookEntry>, Error> {
        return getUserBooksUseCase()
    }

    suspend fun deleteBook(uiData: BookUIDataDTO): Result<Unit, Error>{
        val entry = uiData.toDomain()
        return deleteUserBookUserCase(entry)
    }

}