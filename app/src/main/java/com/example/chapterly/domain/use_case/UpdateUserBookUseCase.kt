package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import javax.inject.Inject

class UpdateUserBookUseCase @Inject constructor (
    private val userLibraryRepository: UserLibraryRepository
){
    suspend operator fun invoke(book: BookEntry): Result<BookEntry, Error> {
        return userLibraryRepository.updateUserBook(book)
    }
}