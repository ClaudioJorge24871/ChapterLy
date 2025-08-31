package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository

class GetBooksByTitleUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(title: String): List<Book>{
        return bookRepository.getBooksByTitle(title)
    }
}