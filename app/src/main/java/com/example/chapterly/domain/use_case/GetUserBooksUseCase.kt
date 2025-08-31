package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository

class GetUserBooksUseCase (
    private val userLibraryRepository: UserLibraryRepository
){
    suspend operator fun invoke(): List<BookEntry>{
        return userLibraryRepository.getUserBooks()
    }
}