package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository

class GetBooksUseCase (
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(): List<Book> {
        return bookRepository.getBooks()
    }
}