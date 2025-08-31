package com.example.chapterly.data.repository

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository

class BookRepositoryImpl : BookRepository{
    private val books = mutableListOf<Book>()

    override suspend fun getBookByISBN(isbn: String): Book? {
        TODO("Not yet implemented")
    }

    override suspend fun getBooksByTitle(title: String): List<Book> {
        TODO("Not yet implemented")
    }

    override suspend fun getBooks(): List<Book> {
        TODO("Not yet implemented")
    }
}