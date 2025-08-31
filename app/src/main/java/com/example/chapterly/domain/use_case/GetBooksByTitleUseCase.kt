package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error

class GetBooksByTitleUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(title: String): Result<List<Book>, Error>{
        return bookRepository.getBooksByTitle(title)
    }
}