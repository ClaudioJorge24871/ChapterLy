package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result


class DeleteUserBookUseCase (
    private val userLibraryRepository: UserLibraryRepository
) {
    suspend operator fun invoke(userBook: BookEntry): Result<Unit, Error>{
        return userLibraryRepository.deleteUserBook(userBook)
    }
}