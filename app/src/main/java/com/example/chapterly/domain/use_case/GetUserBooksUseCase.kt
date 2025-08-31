package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error

class GetUserBooksUseCase (
    private val userLibraryRepository: UserLibraryRepository
){
    suspend operator fun invoke(): Result<List<BookEntry>, Error>{
        return userLibraryRepository.getUserBooks()
    }
}