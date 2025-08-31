package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.UserLibraryRepository

class DeleteUserBookUseCase (
    private val userLibraryRepository: UserLibraryRepository
) {
    suspend operator fun invoke(userBook: BookEntry){
        userLibraryRepository.deleteUserBook(userBook)
    }
}