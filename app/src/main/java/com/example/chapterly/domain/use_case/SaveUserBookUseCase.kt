package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository

class SaveUserBookUseCase (
    private val userLibraryRepository: UserLibraryRepository,
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(userBook: BookEntry){
        val book = bookRepository.getBookByISBN(userBook.book.isbn)
        if (book != null){
            val entry = userBook.copy(book = book) // replaces only the book
            userLibraryRepository.saveUserBook(entry)
        }else{
            userLibraryRepository.saveUserBook(userBook)
        }
    }
}