package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository

class GetBooksByISBNUseCase (
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(isbn: String): Book?{
        return bookRepository.getBookByISBN(isbn)
    }
}